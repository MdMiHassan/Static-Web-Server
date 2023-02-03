package server.sack;

import java.io.ByteArrayInputStream;
public interface Sack {
    public ByteArrayInputStream get(String uri);
    public boolean add(String uri);
    public boolean contains(String uri);
}
