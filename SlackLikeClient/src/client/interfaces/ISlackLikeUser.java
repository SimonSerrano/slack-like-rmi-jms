package client.interfaces;


import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface exposing the interactions available with a user object.
 */
public interface ISlackLikeUser extends Remote {
    /**
     * Get the name of the user.
     * @return the name of the user.
     * @throws RemoteException if an RMI exception occurs.
     */
    String getName()throws RemoteException;

}
