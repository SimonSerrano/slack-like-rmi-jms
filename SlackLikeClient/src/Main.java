import handlers.CommandHandler;

class Main {



    /** @noinspection InfiniteLoopStatement*/
    public static void main(String [] args){
        System.out.println("Welcome to SlackLike !");
        CommandHandler handler = new CommandHandler();
        while(true){
           handler.handle();
        }
    }
}
