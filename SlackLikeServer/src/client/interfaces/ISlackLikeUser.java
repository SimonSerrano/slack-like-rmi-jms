package client.interfaces;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ISlackLikeUser extends Remote {
    String getName()throws RemoteException;

}
