package listeners;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Class used to listen to messages from the JMS Provider, simply prints on the standard output.
 */
public class GroupListener implements MessageListener {

    /**
     * {@inheritDoc}
     * Print the message in the standard output.
     * @param message
     */
    @Override
    public void onMessage(Message message) {
        try {
            System.out.println(((TextMessage)message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
