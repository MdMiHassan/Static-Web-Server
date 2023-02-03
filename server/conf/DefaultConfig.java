package server.conf;

public class DefaultConfig {
    public static final long KEEPALIVE_TIMEOUT=60000l;//60s
    public static final long MAX_CACHE_MEMORY=67108864l; //64MB
    public static final long MAX_CACHE_ELEMENT_SIZE=16777216; //16MB
    public static final int BUFFER_SIZE = 8192; //8KB
    public static final int SERVER_PORT = 8080;
}
