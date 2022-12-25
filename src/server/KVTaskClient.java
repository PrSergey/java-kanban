package server;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.nio.charset.StandardCharsets.UTF_8;

public class KVTaskClient {

    URI url;
    String apiToken;

    public KVTaskClient(URI url) throws IOException, InterruptedException {
        this.url = url;
        register();
    }

    public void register() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI urlRegister=URI.create(url+"/register");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(urlRegister)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        apiToken= response.body();
    }

    public void put(String key, String json) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI urlRegister=URI.create(url+"/save"+"/"+key+"?"+"API_TOKEN="+apiToken);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(urlRegister)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response);
    }

    public String load(String key) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI urlRegister=URI.create(url+"/load"+"/"+key+"?"+"API_TOKEN="+apiToken);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(urlRegister)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }


}
