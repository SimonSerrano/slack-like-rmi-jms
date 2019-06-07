package commands;

public enum ECommand {

    CONNECT("!connect","Connect to the server with a username and a password"),
    GROUPS("!groups", "List all the available groups"),
    SUBSCRIBE("!subscribe", "[group-name] Subscribe to a group"),
    LISTEN("!listen", "[group-name] Listen to a group conversation"),
    QUIET("!quiet", "[group-name] Stop listenning to a group conversation"),
    PUBLISH("!publish", "[group-name][message] Publish a message to a group"),
    HELP("!help", "Show this message"),
    EXIT("!exit", "Exit the program");

    private final String commandString;
    private final String commandHelper;

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
