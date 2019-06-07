package commands;

/**
 * Enum class which represent the commands available in the client.
 */
public enum ECommand {

    CONNECT("!connect","Connect to the server with a username and a password."),
    GROUPS("!groups", "List all the available groups."),
    SUBSCRIBE("!subscribe", "[group-name] Subscribe to a group."),
    LISTEN("!listen", "[group-name] Listen to a group conversation."),
    QUIET("!quiet", "[group-name] Stop listenning to a group conversation."),
    PUBLISH("!publish", "[group-name][message] Publish a message to a group."),
    HELP("!help", "Show this message."),
    EXIT("!exit", "Exit the program.");

    private final String commandString;
    private final String commandHelper;

    /**
     * Constructor for enum objects.
     * @param commandString the string to type in the console.
     * @param commandHelper the helper message to display.
     */
    ECommand(String commandString, String commandHelper) {
        this.commandString = commandString;
        this.commandHelper = commandHelper;
    }


    public String getCommandString() {
        return commandString;
    }

    public String getCommandHelper() {
        return commandHelper;
    }
}
