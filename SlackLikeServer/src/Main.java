import client.interfaces.ISlackLikeServer;
import model.SlackLikeServer;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

import java.net.URI;
import java.rmi.Naming;

class Main {
    public static void main(String[] args) throws Exception {

        BrokerService broker = BrokerFactory.createBroker(new URI(
              "broker:(tcp://localhost:61616)"));
        broker.start();

        ISlackLikeServer server =new SlackLikeServer();
        Naming.rebind("rmi://127.0.0.1/slacklike", server);
        System.out.println("[System] Chat Server is ready.");
    }
}
