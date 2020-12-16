
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * This class is for setting up the connection with the server
 * and sends requests to URLs for GET and POST.
 * This class has two methods,HttpClientGet and HttpClientPost
 * @Author Tim Zhang
 */
public class HttpClientGetAndPost {
    private static final String getURL = "https://candidate.hubteam.com/candidateTest/v3/problem/dataset?userKey=41a4bf81c1d07e04b8c428652fcb";
    private static final String postURL = "https://candidate.hubteam.com/candidateTest/v3/problem/result?userKey=41a4bf81c1d07e04b8c428652fcb";
    /**
     * A get method that returns a BufferedReader, which contains the InputStream client requested.
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public String Get() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(getURL))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String status = (response.statusCode()==200)?"Connection successful!":"Error Code: "+response.statusCode();
        System.out.println(status);
        return response.body();
    }

    /**
     * A post method with void return type.
     * Prints out the response information to terminal.
     * @param content Posting content.
     * @throws IOException
     * @throws InterruptedException
     */
    public void Post(String content) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(postURL))
                .POST(HttpRequest.BodyPublishers.ofString(content)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String status = (response.statusCode()==200)?"Connection successful!":"Error Code: "+response.statusCode();
        System.out.println(status);
        System.out.println(response.body());
    }
}
