import handlers.ConnectionHandler;

class Main {



    /** @noinspection InfiniteLoopStatement*/
    public static void main(String [] args){
        System.out.println("Welcome to SlackLike !");
        ConnectionHandler handler = new ConnectionHandler();
        while(true){
           handler.handle();
        }
    }
}
