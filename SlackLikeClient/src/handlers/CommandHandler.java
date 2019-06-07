package handlers;

import commands.ECommand;
import client.interfaces.ISlackLikeServer;
import client.interfaces.ISlackLikeUser;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

public class CommandHandler {

    private Scanner console;
    private boolean connected;

    public CommandHandler() {
        console = new Scanner(System.in);
        connected = false;
    }

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
                    handler.handle();
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
