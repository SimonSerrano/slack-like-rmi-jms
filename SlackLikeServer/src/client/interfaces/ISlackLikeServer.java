package client.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

/**
 * Interface exposing the interactions available with the server.
 */
public interface ISlackLikeServer extends Remote {

    /**
     * Log in a user with a username and a password.
     * @param username The username of the account.
     * @param password The password of the account(Currently does not matter, it could be anything).
     * @return the user if found.
     * @throws RemoteException if an RMI exception occurs.
     */
    ISlackLikeUser login(String username, byte[] password)throws RemoteException;

    /**
     * Get the list of groups instantiated in the server.
     * @return the list of groups.
     * @throws RemoteException  if an RMI exception occurs.
     */
    Vector<IGroup> getGroups() throws RemoteException;

    /**
     * Get the group from its name.
     * @param name the name of the group.
     * @return the group with the corresponding name.
     * @throws RemoteException  if an RMI exception occurs.
     */
    IGroup getGroupByName(String name) throws RemoteException;

}
