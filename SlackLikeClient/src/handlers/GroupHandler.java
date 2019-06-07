package handlers;

import commands.ECommand;
import client.interfaces.IGroup;
import client.interfaces.ISlackLikeServer;
import client.interfaces.ISlackLikeUser;
import listeners.GroupListener;

import javax.jms.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class GroupHandler {

    private ISlackLikeUser user;
    private ISlackLikeServer server;
    private Scanner scanner;
    private Connection connection;
    private Session subSession;
    private List<IGroup> groups;

    private final long MESSAGE_LIFESPAN = 180000;


    public GroupHandler(ISlackLikeUser user, ISlackLikeServer server, Connection connection) throws RemoteException, JMSException {
        this.user = user;
        this.server = server;
        this.scanner = new Scanner(System.in);
        this.connection = connection;
        subSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }


    public void handle() {
        String[] command = scanner.nextLine().split(" ");
        if(command[0].equals(ECommand.GROUPS.getCommandString())){
            try {
                groups = server.getGroups();
                if (groups != null) {
                    for (IGroup group : groups) {
                        String name = group.getName();
                        if (group.getSubscribers().contains(user)) {
                            name += " *";
                        }
                        System.out.println(name);
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        else if(command[0].equals(ECommand.SUBSCRIBE.getCommandString())){
            if(command.length > 1 && !command[1].equals("")){
                Optional<IGroup> group = findGroup(command[1]);
                group.ifPresent((group1 -> {
                    try {
                        subscribe(group1);
                    } catch (RemoteException | JMSException e) {
                        e.printStackTrace();
                    }
                }));
            }else {
                throw new IllegalArgumentException();
            }
        }
        else if(command[0].equals(ECommand.LISTEN.getCommandString())){
            if(command.length > 1 && !command[1].equals("")){
                Optional<IGroup> group = findGroup(command[1]);
                group.ifPresent((group1 -> {
                    try {
                        listen(group1);
                    } catch (JMSException | RemoteException e) {
                        e.printStackTrace();
                    }
                }));
            }else {
                throw new IllegalArgumentException();
            }
        }
        else if(command[0].equals(ECommand.QUIET.getCommandString())){
            try {
                quiet();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        else if(command[0].equals(ECommand.PUBLISH.getCommandString())){
            if(command.length > 2 && !command[1].equals("")){
                StringBuilder message = new StringBuilder();
                for (int i = 2; i < command.length; i++) {
                    message.append(command[i]).append(" ");
                }
                Optional<IGroup> group = findGroup(command[1]);
                group.ifPresent((group1 -> {
                    try {
                        publish(message.toString(), group1);
                    } catch (RemoteException | JMSException e) {
                        e.printStackTrace();
                    }
                }));

            }else {
                throw new IllegalArgumentException();
            }
        }
        else if(command[0].equals(ECommand.EXIT.getCommandString())){
            try {
                if(subSession != null){
                    subSession.close();
                }
                if(connection != null){
                    connection.close();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
        else if(command[0].equals(ECommand.HELP.getCommandString())){
            for (ECommand eCommand : ECommand.values()) {
                System.out.println(eCommand.getCommandString() + "\t" + eCommand.getCommandHelper());
            }
        }else {
            System.out.println("Type !help to see the help menu");
        }
    }

    private void quiet() throws JMSException {
        if(subSession != null){
            subSession.close();
        }
    }

    private void publish(String message, IGroup group) throws RemoteException, JMSException {
        if(group.getSubscribers().contains(user)) {
            Session pubSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = pubSession.createTopic(group.getName());
            MessageProducer producer = pubSession.createProducer(topic);
            TextMessage textMessage = pubSession.createTextMessage();
            textMessage.setText("#" + group.getName() + " - " + user.getName() + ": " + message);
            producer.send(textMessage, DeliveryMode.PERSISTENT, Message.DEFAULT_PRIORITY, MESSAGE_LIFESPAN);
            pubSession.close();
        }else {
            System.out.println("You need to be subscribed in order to publish");
        }
    }

    private void subscribe(IGroup group) throws RemoteException, JMSException {
        group.subscribe(user);
        System.out.println("Successfully subscribed user " + user.getName() + " to " + group.getName());
    }


    private void listen(IGroup group) throws JMSException, RemoteException {
        if(group.getSubscribers().contains(user)) {
            Topic topic = subSession.createTopic(group.getName()+user.getName());
            MessageConsumer consumer = subSession.createDurableSubscriber(topic, user.getName());
            consumer.setMessageListener(new GroupListener());
            connection.start();
        }else {
            System.out.println("You need to be subscribed to a group in order to listen to it");
        }
    }


    private Optional<IGroup> findGroup(String name){
        if(groups!=null && !groups.isEmpty()){
            for (IGroup group : groups) {
                try {
                    if (group.getName().equals(name)){
                        return Optional.of(group);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Group not found");
        }else {
            try {
                IGroup group = server.getGroupByName(name);
                return Optional.of(group);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }



}
