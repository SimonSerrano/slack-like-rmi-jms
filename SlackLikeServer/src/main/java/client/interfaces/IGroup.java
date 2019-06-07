package client.interfaces;

import javax.jms.JMSException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

/**
 * Interface exposing the interactions available with a group object.
 */
public interface IGroup extends Remote {

    /**
     * Get the name of the group.
     * @return the name of the group.
     * @throws RemoteException if an RMI exception occurs
     */
    String getName() throws RemoteException;

    /**
     * Get the list of subscribers of the group.
     * @return the list of subs.
     * @throws RemoteException if an RMI exception occurs.
     */
    Vector<ISlackLikeUser> getSubscribers() throws RemoteException;

    /**
     * Subscribe a user to the group.
     * @param user user to subscribe to a group.
     * @throws RemoteException if an RMI exception occurs.
     */
    void subscribe(ISlackLikeUser user) throws RemoteException, JMSException;

    /**
     * Unsubscribe a user to the group.
     * @param user the user to unsubscribe.
     * @throws RemoteException if an RMI exception occurs.
     */
    void unsubscribe(ISlackLikeUser user) throws RemoteException;

}
