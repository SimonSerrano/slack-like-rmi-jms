package utils;

import client.interfaces.ISlackLikeUser;
import model.SlackLikeUser;

import java.rmi.RemoteException;
import java.util.Vector;

/**
 * Represent the client mock utility class.
 */
public class ClientMock {

    /**
     * Build the client mocks for the server.
     * @return the vector of clients.
     */
    public Vector<ISlackLikeUser> buildClientMocks(){
        Vector<ISlackLikeUser> clients = new Vector<>();
        try {
            clients.add(new SlackLikeUser("Simon"));
            clients.add(new SlackLikeUser("Bobby"));
            clients.add(new SlackLikeUser("Robby"));
            clients.add(new SlackLikeUser("Alone"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return clients;
    }


}
