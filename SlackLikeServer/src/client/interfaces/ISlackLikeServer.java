package client.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface ISlackLikeServer extends Remote {

    ISlackLikeUser login(String username, byte[] password)throws RemoteException;
    Vector<IGroup> getGroups() throws RemoteException;
    IGroup getGroupByName(String name) throws RemoteException;

}
