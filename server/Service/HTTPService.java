package server.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import server.Context;
import server.http.HTTP;
import server.http.HTTPRequest;
import server.http.HTTPResponse;
import util.Logger;

public class HTTPService implements Service {
    private Socket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    private Context context;

    public HTTPService(Socket socket, Context context) throws IOException {
        this.socket = socket;
        this.context = context;
        this.outputStream = socket.getOutputStream();
        this.inputStream = socket.getInputStream();
        this.socket.setSoTimeout(60000);
    }

    @Override
    public void serve() {
        try {
            HTTPRequest request = new HTTPRequest(inputStream);
            if (request.cookRequest()) {
                HTTPResponse response = HTTPResponse.of(request, context);
                if (response != null) {
                    response.stream(outputStream);
                    System.out.println("served");
                }
            } else {
                Logger.show("Request not parsed correctly!");
            }
            // deciding what to do next
            if (request!=null&&request.connection == HTTP.KEEP_ALIVE) {
                serve();
            } else {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
