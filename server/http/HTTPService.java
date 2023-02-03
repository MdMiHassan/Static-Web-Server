package server.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import server.Service.Service;
import util.Logger;

public class HTTPService implements Service {
    private HTTPRequest request;
    private HTTPResponse response;
    private Socket socket;
    private OutputStream outputStream;
    private InputStream inputStream;

    public HTTPService(Socket socket) throws IOException {
        this.socket = socket;
        this.outputStream=socket.getOutputStream();
        this.inputStream=socket.getInputStream();
        this.socket.setSoTimeout(60000);
    }

    @Override
    public void serve() {
        try {
            if (readRequest()) {
                sendResponse();
            } else {
                Logger.show("Request not parsed correctly!");
            }
            if (request.connection==HTTP.KEEP_ALIVE) {
                serve();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean readRequest() {
            request = new HTTPRequest(inputStream);
            return request.cookRequest();
    }

    private boolean sendResponse() throws IOException {
        return response.stream(outputStream);
    }
}
