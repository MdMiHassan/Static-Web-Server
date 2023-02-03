package server;
public class Server {
    public static void run(Context context) {
        new Thread(new Waiter(context), "waiter").start();
    }
}
