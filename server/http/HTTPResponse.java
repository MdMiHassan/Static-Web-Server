package server.http;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import server.Context;
import server.Response;
import server.conf.DefaultConfig;
import server.sack.SackEngine;
import util.Utility;

public class HTTPResponse implements Response {

    private String[] header;
    private LinkedList<HTTPResponseBody> responseBodies;

    private HTTPResponse() {
        this.header = new String[10];
        this.responseBodies = new LinkedList<>();
    }

    public static HTTPResponse of(HTTPRequest request, Context context) {
        if (request == null) {
            return null;
        }
        try {
            HTTPResponse response = new HTTPResponse();
            HTTPRequestLine requestLine;
            if ((requestLine = request.getRequestLine()) == null) {
                return null;
            }

            SackEngine sack = context.getSackEngine();
            String absPath = context.getPublicDir() + requestLine.getResourcePath();
            System.out.println(absPath);
            int psIndex = absPath.lastIndexOf(context.getPathSeparator());
            if (psIndex == absPath.length() - 1) {
                absPath += DefaultConfig.DEFAULT_PUBLIC_RESOURCE;
            }
            if (sack.contains(absPath)) {
                byte[] res=sack.get(absPath);
                response.setResponseStatus(201);
                response.setContentLength(res.length);
                response.setAcceptRanges(true);
            } else {
                File file = new File(absPath);
                if (file.exists() && file.isFile()) {
                    response.setResponseStatus(200);
                    response.setAcceptRanges(true);
                    response.setContentLength(file.length());
                    response.setResponseBody(new HTTPResponseBody(file, 0, file.length()));
                } else {
                    response.setResponseStatus(404);
                    response.setAcceptRanges(true);
                }
            }
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setAcceptRanges(boolean accept) {
        header[HTTP.ACCEPT_RANGES] = "Accept-Ranges: " + (accept ? "bytes" : "none") + "\r\n";
    }

    public void setResponseStatus(int responseCode) {
        header[HTTP.RESPONSE_STATUS] = HTTP.getResponseStatus(responseCode);
    }

    public void setContentLength(long length) {
        header[HTTP.CONTENT_LENGTH] = "Content-Length: " + length + "\r\n";
    }

    public void setCacheControl() {
        header[HTTP.CACHE_CONTROL] = "Cache-Control: public, max-age=0\r\n";
    }

    public void setEtag(File file) {
        header[HTTP.ETAG] = "ETag: W/\"" + Utility.generateETagValue(file) + "\"\r\n";
    }

    public void setCookie(String key, String value) {
        header[HTTP.SET_COOKIE] = "Set-Cookie: " + key + "=" + value + "\r\n";
    }

    public void setCookie(String key, String value, long milliSecond) {
        SimpleDateFormat dateFormater = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
        String date = dateFormater.format(new Date(new Date().getTime() + milliSecond));
        header[HTTP.SET_COOKIE] = "Set-Cookie: " + key + "=" + value + "; Expires=" + date + ";\r\n";
    }

    public void setContentType(String contentType) {
        header[HTTP.CONTENT_TYPE] = "Content-Type: " + contentType + "\r\n";
    }

    public void setConnection(boolean close) {
        header[HTTP.CONTENT_TYPE] = "Connection: " + (close ? "close" : "keep-alive") + "\r\n";
    }

    public void setResponseBody(HTTPResponseBody responseBody) {
        responseBodies.add(responseBody);
    }

    public void setContentRange(long rangeStart, long rangeEnd, long messageSize) {
        header[HTTP.CONTENT_RANGE] = "Content-Range: byte " + rangeStart
                + "-" + rangeEnd + "/" + messageSize + "\r\n";
    }

    private String getHeader() {
        StringBuilder messageHeader = new StringBuilder();
        for (int i = 0; i < this.header.length; i++) {
            String h = this.header[i];
            if (h != null) {
                messageHeader.append(h);
            }
        }
        messageHeader.append("\r\n");
        System.out.println(messageHeader.toString());
        return messageHeader.toString();
    }

    public boolean stream(OutputStream outputStream) {
        try {
            outputStream.write(getHeader().getBytes("UTF-8"));
            HTTPResponseBody rb;
            while ((rb = responseBodies.poll()) != null) {
                InputStream body = rb.getInputStream();
                int bufferSize = DefaultConfig.BUFFER_SIZE;
                byte[] buffer = new byte[bufferSize];
                long offset = rb.getRangeStart();
                body.skip(rb.getRangeStart());
                while (offset + buffer.length <= rb.getRangeEnd()) {
                    body.read(buffer);
                    outputStream.write(buffer);
                    offset += bufferSize;
                }
                if (offset < rb.getRangeEnd()) {
                    body.read(buffer, 0, (int) (rb.getRangeEnd() - offset));
                    outputStream.write(buffer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
