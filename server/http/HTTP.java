package server.http;

public interface HTTP {
    public static final int[] VERSION_1_0 = { 72, 84, 84, 80, 47, 49, 46, 48 };
    public static final int[] VERSION_1_1 = { 72, 84, 84, 80, 47, 49, 46, 49 };
    public static final int[] VERSION_2 = { 72, 84, 84, 80, 47, 50 };
    public static final int[] VERSION_3 = { 72, 84, 84, 80, 47, 51 };
    public static final short V_US = 20000;
    public static final short V_1_0 = 20001;
    public static final short V_1_1 = 20002;
    public static final short V_2 = 20003;
    public static final short V_3 = 20004;
    /* header fields */
    public static final short APPLICATION_OCTET_STREAM = 10001; // application/octet-stream
    public static final short TEXT_PLAIN = 10002; // text/plain
    public static final short TEXT_CSS = 10003; // text/css
    public static final short TEXT_HTML = 10004; // text/html
    public static final short TEXT_JAVASCRIPT = 10005; // text/javascript
    public static final short IMAGEA_PNG = 10006; // image/apng: Animated Portable Network Graphics (APNG)
    public static final short IMAGE_AVIF = 10007; // image/avif : AV1 Image File Format (AVIF)
    public static final short IMAGE_GIF = 10008; // image/gif: Graphics Interchange Format (GIF)
    public static final short IMAGE_JPEG = 10009; // image/jpeg: Joint Photographic Expert Group image (JPEG)
    public static final short IMAGE_PNG = 10010; // image/png: Portable Network Graphics (PNG)
    public static final short IMAGE_SVGXML = 10011; // image/svg+xml: Scalable Vector Graphics (SVG)
    public static final short IMAGE_WEBP = 10012; // image/webp: Web Picture format (WEBP)
    public static final short AUDIO_WAVE = 10013; //
    public static final short AUDIO_WAV = 10014; //
    public static final short AUDIO_XWAV = 10015; //
    public static final short AUDIO_XPNWAV = 10016; //
    public static final short AUDIO_WEBM = 10017; // audio/webm
    public static final short AUDIO_OGG = 10018; // audio/ogg
    public static final short VIDEO_OGG = 10019; // video/ogg
    public static final short APPLICATION_OGG = 10020; //
    public static final short MULTIPART_FORMDATA = 10021; // multipart/form-data: The multipart/form-data type can be
                                                          // used when sending the values of a completed HTML Form from
                                                          // browser to server
    public static final short MULTIPART_BYTERANGES = 10022; // multipart/byteranges

    public static final short KEEP_ALIVE = 11001; // keep-alive
    public static final short CONNECTION_CLOSED = 11002; // keep-alive
    public static final short CONNECTION_UPGRADE = 11003; // keep-alive

    /* headers */
    public static final short RESPONSE_STATUS = 0;
    public static final short ACCEPT_RANGES = 1;
    public static final short CONTENT_LENGTH = 2;
    public static final short CACHE_CONTROL = 3;
    public static final short ETAG = 4;
    public static final short SET_COOKIE = 5;
    public static final short CONTENT_TYPE = 6;
    public static final short CONNECTION = 7;
    public static final short CONTENT_RANGE = 8;

    /* Request Methods */
    // GET: requests a representation of the specified resource. GET requests are
    // the most common and widely used method.
    // POST: submits an entity to the specified resource, often causing a change in
    // state or side effects on the server.
    // PUT: uploads a representation of the specified resource.
    // DELETE: deletes the specified resource.
    // HEAD: same as GET, but only requests the headers of a response.
    // OPTIONS: describes the communication options for the target resource.
    // PATCH: applies partial modifications to a resource.
    // TRACE: performs a message loop-back test along the path to the target
    // resource.
    // CONNECT: establishes a tunnel to the server identified by the target
    // resource.

    public static final short REQUEST_METHOD_GET = 12001;
    public static final short REQUEST_METHOD_POST = 12002;
    public static final short REQUEST_METHOD_PUT = 12003;
    public static final short REQUEST_METHOD_DELETE = 12004;
    public static final short REQUEST_METHOD_HEAD = 12005;
    public static final short REQUEST_METHOD_OPTIONS = 12006;
    public static final short REQUEST_METHOD_PATCH = 12007;
    public static final short REQUEST_METHOD_TRACE = 12008;
    public static final short REQUEST_METHOD_CONNECT = 12009;
    // public static final short =10001; //
    // public static final short =10001; //
    public static final int ASCII_CARRIAGE_RETURN = 13; // '\r'
    public static final int ASCII_NEW_LINE = 10; // '\n'
    public static final int ASCII_COLON = 58; // ':'
    public static final int ASCII_WHITE_SPACE = 32; // ':'

    public static final int[] HN_CONNECTION = { 99, 111, 110, 110, 101, 99, 116, 105, 111, 110 };
    public static final int[] HN_RANGE = { 114, 97, 110, 103, 101 };
    public static final int[] HN_IF_RANGE = { 105, 102, 45, 114, 97, 110, 103, 101 };
    public static final int[] HN_IF_NONE_MATCH = { 105, 102, 45, 110, 111, 110, 101, 45, 109, 97, 116, 99, 104 };

    public static String getResponseStatus(int responseCode) {
        switch (responseCode) {
            case 200:
                return "HTTP/1.1 200 OK\r\n";
            case 404:
                return "HTTP/1.1 404 Not Found\r\n";
            case 206:
                return "HTTP/1.1 206 Partial Content\r\n";
            case 304:
                return "HTTP/1.1 304 Not Modified\r\n";
            default:
                return "HTTP/1.1 400 Bad Request\r\n";
        }
    }

    public static String getContentType(String fileExtension) {
        fileExtension = fileExtension.toLowerCase();
        switch (fileExtension) {
            case "html":
                return "text/html; charset=utf-8";
            case "js":
                return "text/javascript";
            case "css":
                return "text/css";
            case "svg":
                return "image/svg+xml";
            case "png":
                return "image/png";
            case "txt":
                return "text/plain";
            case "mp3":
                return "audio/mpeg";
            case "mp4":
                return "video/mp4";
            case "json":
                return "application/json";
            case "pdf":
                return "application/pdf";
            case "jpeg":
                return "image/jpeg";
            case "jpg":
                return "image/jpeg";
            case "ppt":
                return "application/vnd.ms-powerpoint";
            case "zip":
                return "application/zip";
            case "xml":
                return "application/xml";
            case "xhtml":
                return "application/xhtml+xml";
            case "xls":
                return "application/vnd.ms-excel";
            default:
                return "application/octet-stream";
        }
    }

    public static boolean deepEqualNCS(int[] buffer, int bOffset, int bEnd, int[] hn) {
        if (bEnd - bOffset != hn.length) {
            return false;
        }
        for (int i = 0; i < hn.length; i++) {
            if (toLowerCase(buffer[bOffset + i]) != toLowerCase(hn[i])) {
                return false;
            }
        }
        return true;
    }

    private static int toLowerCase(int c) {
        if (64 < c && c < 91) {
            return (c | 32);
        }
        return c;
    }
}
