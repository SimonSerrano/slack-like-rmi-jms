import handlers.CommandHandler;

public class Main {



    public static void main(String [] args){
        System.out.println("Welcome to SlackLike !");
        CommandHandler handler = new CommandHandler();
        while(true){
           handler.handle();
        }
    }
}
