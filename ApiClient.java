package Literatura.lib.scr.main.java;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

public class ApiClient {
    private final OkHttpClient client;
    private final String apiUrl = "https://api.gundex.com"; // URL ficticia

    public ApiClient() {
        client = new OkHttpClient();
    }

    public String searchBooks(String query) throws IOException {
        Request request = new Request.Builder()
                .url(apiUrl + "/search?q=" + query)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        }
    }
}
