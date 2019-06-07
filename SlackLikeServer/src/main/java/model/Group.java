package model;

import client.interfaces.IGroup;
import client.interfaces.ISlackLikeUser;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

/**
 * Represent the implementation of a group containing subscribers, which are SlackLikeUser, and the group has a name.
 */
public class Group extends UnicastRemoteObject implements IGroup, MessageListener {

    private final String name;
    private final Vector<ISlackLikeUser> subscribers;
    private Connection connection;
    private Session subSession;

    private final long MESSAGE_LIFESPAN = 180000;

    /**
     * Constructor of a group with a name.
     * @param name the name of the group.
     * @throws RemoteException if an exception occurs.
     */
    public Group(String name) throws RemoteException, JMSException {
        this.name = name;
        this.subscribers = new Vector<>();
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        connection = connectionFactory.createConnection();
        connection.setClientID(this.name);
        connection.start();
        subSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = subSession.createTopic(this.name);
        MessageConsumer consumer = subSession.createDurableSubscriber(topic, this.name);
        consumer.setMessageListener(this);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Vector<ISlackLikeUser> getSubscribers() {
        return subscribers;
    }



    /**
     *{@inheritDoc}
     */
    @Override
    public void subscribe(ISlackLikeUser user) throws RemoteException, JMSException {
        if (!subscribers.contains(user)) {
            Topic topic = subSession.createTopic(name+user.getName());
            subSession.createDurableSubscriber(topic, user.getName());
            subscribers.add(user);
            System.out.println("Successfully subscribed " + user.getName() + " to group " + name);
        }else {
            System.out.println("Client is already subscribed to this group");
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void unsubscribe(ISlackLikeUser user) throws RemoteException {
        if(subscribers.contains(user)){
            subscribers.remove(user);
            System.out.println("Successfully unsubscribed " + user.getName() + " to group " + name);
        }else {
            System.out.println("Client is not in the group");
        }

    }


    /**
     * {@inheritDoc}
     * Transfers the message from the group's topic to each subscribers to a different topic.
     */
    @Override
    public void onMessage(Message message) {
        Session pubSession;
        try {
            pubSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Session finalPubSession = pubSession;
            subscribers.forEach((user -> {
                try {
                    Topic topic = finalPubSession.createTopic(name + user.getName());
                    MessageProducer producer = finalPubSession.createProducer(topic);
                    producer.send(message, DeliveryMode.PERSISTENT, Message.DEFAULT_PRIORITY, MESSAGE_LIFESPAN);

                } catch (RemoteException | JMSException e) {
                    e.printStackTrace();
                }
            }));
            finalPubSession.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
