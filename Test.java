import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class Test {
    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
        // Socket s=new Socket("localhost", 8080);
        // s.getOutputStream().write("\r".getBytes("UTF-8"));
        char[] b = "\n".toCharArray();
        for (int i = 0; con(i, b.length); i = inc(i)) {
            System.out.print(((int) b[i]) + ",");
        }
        try {
            HttpRequest request2 = HttpRequest.newBuilder()
                    .uri(new URI("https://postman-echo.com/get"))
                    .header("key1", "value1")
                    .header("key2", "value2")
                    .GET()
                    .build();
                    HttpClient client=HttpClient.newHttpClient();
                    HttpResponse<String> response = client.send(request2, BodyHandlers.ofString());
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static int inc(int i) {
        System.out.println("incr");
        return i + 1;
    }

    public static boolean con(int x, int y) {
        System.out.println("checked");
        return x < y;
    }
}