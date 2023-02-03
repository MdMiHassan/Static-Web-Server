import server.Context;
import server.Server;

public class Starter {
    public static void main(String[] args) {
        Server.run(new Context(args));
    }
}