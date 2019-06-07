package model;

import client.interfaces.IGroup;
import client.interfaces.ISlackLikeUser;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

/**
 * Represent the implementation of a group containing subscribers, which are SlackLikeUser, and the group has a name
 */
public class Group extends UnicastRemoteObject implements IGroup {

    private String name;
    private Vector<ISlackLikeUser> subscribers;


    /**
     * Constructor of a group with a name
     * @param name the name of the group
     * @throws RemoteException if an exception occurs
     */
    public Group(String name) throws RemoteException {
        this.name = name;
        this.subscribers = new Vector<>();
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
    public Vector<ISlackLikeUser> getSubscribers() throws RemoteException {
        return subscribers;
    }



    /**
     *{@inheritDoc}
     */
    @Override
    public void subscribe(ISlackLikeUser user) throws RemoteException {
        if (!subscribers.contains(user)) {
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



}
