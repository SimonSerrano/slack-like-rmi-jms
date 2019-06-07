package utils;

import client.interfaces.IGroup;
import client.interfaces.ISlackLikeUser;
import model.Group;

import javax.jms.JMSException;
import java.rmi.RemoteException;
import java.util.Random;
import java.util.Vector;

/**
 * Represent the group mock utility class
 */
public class GroupMock {

    /**
     * Build the groups mock for the server and randomly adds subscribers
     * @return the vector of groups
     */
    public Vector<IGroup> buildGroupMocks(Vector<ISlackLikeUser> clients){
        Vector<IGroup> groups = new Vector<>();
        try {
            groups.add(new Group("middleware"));
            groups.add(new Group("pns-innov"));
            groups.add(new Group("ai"));
            groups.add(new Group("iot"));
            groups.add(new Group("road-to-si5"));
            for (IGroup group : groups) {
                randomlySubscribe(clients, group);
            }
        } catch (RemoteException | JMSException e) {
            e.printStackTrace();
        }
        return groups;
    }

    private void randomlySubscribe(Vector<ISlackLikeUser> clients, IGroup group) throws RemoteException, JMSException {
        Random random = new Random();
        int numberOfSubs = random.nextInt(clients.size());
        for (int i = 0; i < numberOfSubs; i++) {
            int index = random.nextInt(clients.size());
            group.subscribe(clients.get(index));
        }
    }
}
