package server.http;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class HTTPResponseBody {
    private InputStream inputStream;
    private long start;
    private long end;
    private long size;

    HTTPResponseBody(byte[] source, int start, int end) {
        this.inputStream = new ByteArrayInputStream(source);
        this.start = start;
        this.end = end;
        this.size = source.length;
    }

    HTTPResponseBody(File source, long start, long end) throws IOException {
        this.inputStream = new FileInputStream(source);
        this.start = start;
        this.end = end;
        this.size = source.length();
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public long getRangeStart() {
        return start;
    }

    public long getRangeEnd() {
        return end;
    }

    public long getRangeSize() {
        return size;
    }
}