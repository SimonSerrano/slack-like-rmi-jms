package model;

import client.interfaces.ISlackLikeUser;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class SlackLikeUser extends UnicastRemoteObject implements ISlackLikeUser {


    private final String username;

    public SlackLikeUser(String username) throws RemoteException {
        this.username = username;
    }


    @Override
    public String getName() {
        return username;
    }



}
