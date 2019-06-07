package model;

import client.interfaces.IGroup;
import client.interfaces.ISlackLikeUser;
import client.interfaces.ISlackLikeServer;
import utils.ClientMock;
import utils.GroupMock;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.NoSuchElementException;
import java.util.Vector;

public class SlackLikeServer extends UnicastRemoteObject implements ISlackLikeServer {


    private Vector<ISlackLikeUser> clients;
    private Vector<IGroup> groups;

    public SlackLikeServer() throws RemoteException {
        clients = new ClientMock().buildClientMocks();
        groups = new GroupMock().buildGroupMocks(clients);
    }


    private ISlackLikeUser matchUsernameAndPassword(String username, byte[] password) throws RemoteException {
        //TODO Implement password hash checking
        for (ISlackLikeUser client : clients) {
            if(client.getName().equals(username)){
                return client;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public ISlackLikeUser login(String username, byte[] password) throws RemoteException {
            ISlackLikeUser client = matchUsernameAndPassword(username,password);
            System.out.println(client.getName() + "  got connected....");
            return client;
    }



    @Override
    public Vector<IGroup> getGroups() {
        return groups;
    }

    @Override
    public IGroup getGroupByName(String name) throws RemoteException {
        for (IGroup group : groups) {
            if(group.getName().equals(name)){
                return group;
            }
        }
        throw new IllegalArgumentException();
    }

}
