package handlers;

import client.interfaces.ISlackLikeServer;
import client.interfaces.ISlackLikeUser;
import commands.ECommand;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import java.nio.charset.StandardCharsets;
import java.rmi.Naming;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Scanner;

/**
 * Handles all the commands before the connection.
 */
public class ConnectionHandler {

    private final Scanner console;

    public ConnectionHandler() {
        console = new Scanner(System.in);
    }

    /**
     * Handles the connection process.
     @noinspection InfiniteLoopStatement*/
    public void handle(){
        String[] line = console.nextLine().split(" ");
        if(line[0].equals(ECommand.CONNECT.getCommandString())){
            System.out.print("Enter your username : ");
            String username = console.nextLine();
            System.out.print("Enter your password (for testing purpose, the password can be anything) : ");
            String password = console.nextLine();
            try {
                ISlackLikeServer server = (ISlackLikeServer)Naming.lookup("rmi://127.0.0.1/slacklike");
                SecureRandom random = new SecureRandom();
                byte[] salt = new byte[16];
                random.nextBytes(salt);
                MessageDigest md;
                md = MessageDigest.getInstance("SHA-512");
                md.update(salt);
                byte[] passwordHashed = md.digest(password.getBytes(StandardCharsets.UTF_8));
                ISlackLikeUser user = server.login(username, passwordHashed);
                System.out.println("Succefully connected " + user.getName() + " to the server !");
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
                Connection connection = connectionFactory.createConnection();
                connection.setClientID(user.getName());
                connection.start();
                GroupHandler handler = new GroupHandler(user, server, connection);
                while (true){
                    try {
                        handler.handle();
                    }catch (IllegalArgumentException e){
                        System.out.println("Wrong use of command, type !help to show help menu");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else if (line[0].equals(ECommand.EXIT.getCommandString())){
            System.exit(0);
        }
        else if(line[0].equals(ECommand.HELP.getCommandString())){
            for (ECommand eCommand : ECommand.values()) {
                System.out.println(eCommand.getCommandString() + "\t" + eCommand.getCommandHelper());
            }
        }
        else{
            System.out.println("You need to be connected or type !help to see the help menu");
        }
    }
}
