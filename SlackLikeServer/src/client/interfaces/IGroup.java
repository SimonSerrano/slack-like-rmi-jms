package client.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface IGroup extends Remote {

    String getName() throws RemoteException;

    Vector<ISlackLikeUser> getSubscribers() throws RemoteException;


    void subscribe(ISlackLikeUser client) throws RemoteException;

    void unsubscribe(ISlackLikeUser client) throws RemoteException, RemoteException;

}
