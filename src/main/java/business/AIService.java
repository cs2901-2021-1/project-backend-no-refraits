package business;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.entities.PredictionResponse;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

@Service
@Transactional
@NoArgsConstructor
public class AIService {
    String urlAIServer = "https://b8acm33416.execute-api.us-east-2.amazonaws.com/test/analytics/predict_next_semester";

    public PredictionResponse getPrediction (String course) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(urlAIServer + "?course=" + course))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Logger.getLogger(AIService.class.getName()).warning(response.body());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), new TypeReference<>() {});
    }

}
