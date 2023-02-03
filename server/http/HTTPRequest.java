package server.http;

import java.io.InputStream;
import java.io.InputStreamReader;

import server.conf.DefaultConfig;
import util.Logger;

public class HTTPRequest {
    private InputStream inputStream;
    private HTTPRequestLine requestLine;
    public HTTPRequestLine getRequestLine() {
        return requestLine;
    }

    public long connection;
    public long contentLength;
    public byte[] body;

    public HTTPRequest(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    // A Request-line
    // Zero or more header (General|Request|Entity) fields followed by CRLF
    // An empty line (i.e., a line with nothing preceding the CRLF)
    // indicating the end of the header fields
    // Optionally a message-body
    public boolean cookRequest() {
        try {
            InputStreamReader iStreamReader = new InputStreamReader(inputStream);
            int[] buffer = new int[DefaultConfig.BUFFER_SIZE];
            int bufferSize = 0;
            for (int nc = 0; nc < 2;) {
                int c = iStreamReader.read();
                if (c < 0) {
                    continue;
                }
                if (c != HTTP.ASCII_CARRIAGE_RETURN && c != HTTP.ASCII_NEW_LINE) {
                    buffer[bufferSize++] = c;
                    nc = 0;
                } else {
                    if (c == HTTP.ASCII_NEW_LINE) {
                        nc++;
                        buffer[bufferSize++] = HTTP.ASCII_NEW_LINE;
                    }
                }
            }
            if (bufferSize > 0) {
                for (int i = 0; i < bufferSize - 1; i++) {
                    System.out.print(buffer[i] + ",");
                }
                bufferSize = bufferSize - 1;
                parseHeader(buffer, parseRequestLine(buffer, 0, bufferSize), bufferSize);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private int parseRequestLine(int[] buffer, int offset, int bufferSize) {
        requestLine = new HTTPRequestLine();
        StringBuilder s = new StringBuilder();
        for (; offset < bufferSize && buffer[offset] != HTTP.ASCII_NEW_LINE; offset++) {
            if (buffer[offset] == HTTP.ASCII_WHITE_SPACE) {
                String m = s.toString();
                if (m.equalsIgnoreCase("get")) {
                    requestLine.setRequestMethod(HTTP.REQUEST_METHOD_GET);
                } else if (m.equalsIgnoreCase("post")) {
                    requestLine.setRequestMethod(HTTP.REQUEST_METHOD_POST);
                } else if (m.equalsIgnoreCase("put")) {
                    requestLine.setRequestMethod(HTTP.REQUEST_METHOD_PUT);
                } else if (m.equalsIgnoreCase("delete")) {
                    requestLine.setRequestMethod(HTTP.REQUEST_METHOD_DELETE);
                } else if (m.equalsIgnoreCase("options")) {
                    requestLine.setRequestMethod(HTTP.REQUEST_METHOD_OPTIONS);
                } else if (m.equalsIgnoreCase("patch")) {
                    requestLine.setRequestMethod(HTTP.REQUEST_METHOD_PATCH);
                } else if (m.equalsIgnoreCase("head")) {
                    requestLine.setRequestMethod(HTTP.REQUEST_METHOD_HEAD);
                } else if (m.equalsIgnoreCase("connect")) {
                    requestLine.setRequestMethod(HTTP.REQUEST_METHOD_CONNECT);
                } else if (m.equalsIgnoreCase("trace")) {
                    requestLine.setRequestMethod(HTTP.REQUEST_METHOD_TRACE);
                } else {
                    Logger.show("Invalid request method!: \"" + m + "\"");
                }
                break;
            }
            s.append(((char) buffer[offset]));
        }
        s = new StringBuilder();
        for (offset += 1; offset < bufferSize && buffer[offset] != HTTP.ASCII_NEW_LINE; offset++) {
            if (buffer[offset] == HTTP.ASCII_WHITE_SPACE) {
                requestLine.parseResource(s.toString());
                break;
            }
            s.append((char) buffer[offset]);
        }
        offset += 1;
        int sp = offset;
        for (; offset < bufferSize && buffer[offset] != HTTP.ASCII_NEW_LINE; offset++) {
        }
        if (HTTP.deepEqualNCS(buffer, sp, offset, HTTP.VERSION_1_1)) {
            requestLine.setRequestProtocol(HTTP.V_1_1);
        } else if (HTTP.deepEqualNCS(buffer, sp, offset, HTTP.VERSION_1_0)) {
            requestLine.setRequestProtocol(HTTP.V_1_0);
        } else if (HTTP.deepEqualNCS(buffer, sp, offset, HTTP.VERSION_2)) {
            requestLine.setRequestProtocol(HTTP.V_2);
        } else if (HTTP.deepEqualNCS(buffer, sp, offset, HTTP.VERSION_3)) {
            requestLine.setRequestProtocol(HTTP.V_3);
        } else {
            requestLine.setRequestProtocol(HTTP.V_US);
            System.out.println(offset);
            Logger.show("Unrecognized HTTP VERSION!" + new String(buffer, sp, offset - sp));
        }
        return offset + 1;
    }

    private void parseHeader(int[] buffer, int offset, int bufferSize) {
        if (offset >= bufferSize) {
            return;
        }

        for (int start = offset; offset < bufferSize && buffer[offset] != HTTP.ASCII_NEW_LINE; offset++) {
            if (buffer[offset] == HTTP.ASCII_COLON) {
                System.out.println("here");
                if (HTTP.deepEqualNCS(buffer, start, offset, HTTP.HN_CONNECTION)) {
                    offset = digestConnection(buffer, offset + 1, bufferSize);
                }
                // else if (HTTP.deepEqualNCS(buffer, offset, pos, HTTP.HN_RANGE)) {
                // } else if (HTTP.deepEqualNCS(buffer, offset, pos, HTTP.HN_IF_RANGE)) {
                // } else if (HTTP.deepEqualNCS(buffer, offset, pos, HTTP.HN_IF_NONE_MATCH)){}
                else {
                    for (offset += 1; offset < bufferSize && buffer[offset] != HTTP.ASCII_NEW_LINE; offset++) {
                    }
                    Logger.show("Unsupported header: " + new String(buffer, start, offset - start));
                    offset += 1;
                }
                break;
            }
        }
        parseHeader(buffer, offset, bufferSize);

    }

    private int digestConnection(int[] buffer, int offset, int bufferSize) {
        StringBuilder s = new StringBuilder();
        for (; offset < bufferSize && buffer[offset] != HTTP.ASCII_NEW_LINE; offset++) {
            if (buffer[offset] == HTTP.ASCII_WHITE_SPACE) {
                continue;
            }
            s.append((char) buffer[offset]);
        }
        if (s.toString().equalsIgnoreCase("keep-alive")) {
            connection = HTTP.KEEP_ALIVE;
        } else if (s.toString().equalsIgnoreCase("close")) {
            connection = HTTP.CONNECTION_CLOSED;
        } else {
            connection = HTTP.CONNECTION_UPGRADE;
        }
        return offset + 1;
    }

    private void digestRange(String token) {

    }

    private void digestIfRange(String token) {

    }

    private void digestIfNoneMatch(String token) {

    }
}
