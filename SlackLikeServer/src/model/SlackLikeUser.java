package model;

import client.interfaces.ISlackLikeUser;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Implementation of a user server-side, a simple class wrapping a username.
 */
public class SlackLikeUser extends UnicastRemoteObject implements ISlackLikeUser {


    private final String username;


    public SlackLikeUser(String username) throws RemoteException {
        this.username = username;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return username;
    }



}
