package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import server.Service.HTTPService;
import server.Service.ServiceExecutor;
import util.Logger;

public class Waiter implements Runnable {
    private Context context;

    public Waiter(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        ServiceExecutor executor = new ServiceExecutor(100);
        try (ServerSocket serverSocket = new ServerSocket(context.getPort(), 100)) {
            System.out.println("Server started at port: " + serverSocket.getLocalPort());
            while (true) {
                Socket socket = serverSocket.accept();
                try {
                    executor.add(new HTTPService(socket,context));
                } catch (Exception e) {

                }
            }
        } catch (SecurityException sE) {
            Logger.show("Server failled to start: "+sE.getMessage());
        } catch (IOException ioE) {
            Logger.show("Server failled to start: I/O exception occured!"+ioE.getMessage());
        } catch (IllegalArgumentException iaE) {
            Logger.show("Server failled to start: "+iaE.getMessage());
        }finally{
            executor.shutdown();
        }
    }
}
