package server;

import java.io.OutputStream;

public interface Response {
    public boolean stream(OutputStream to);
}
