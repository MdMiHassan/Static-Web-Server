package server.sack;

public interface Sack {
    public byte[] get(String uri);
    public boolean add(String uri);
    public boolean contains(String uri);
}
