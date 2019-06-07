import client.interfaces.ISlackLikeServer;
import model.SlackLikeServer;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

import java.net.MalformedURLException;
import java.net.URI;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class Main {
    public static void main(String[] args) throws Exception {

        BrokerService broker = BrokerFactory.createBroker(new URI(
              "broker:(tcp://localhost:61616)"));
        broker.start();
//        java.rmi.registry.LocateRegistry.createRegistry(1099);

        ISlackLikeServer server =new SlackLikeServer();
        Naming.rebind("rmi://127.0.0.1/slacklike", server);
        System.out.println("[System] Chat Server is ready.");
    }
}
