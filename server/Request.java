package server;

import java.io.OutputStream;

public interface Request {
    public void stream(OutputStream outputStream);
}
