package business;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.entities.PredictionResponse;
import data.entities.ReportData;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
@Transactional
@NoArgsConstructor
public class AIService {
    public static final String URL_PREDICT = "https://b8acm33416.execute-api.us-east-2.amazonaws.com/test/analytics/predict_next_semester";
    public static final String URL_REPORT = "https://b8acm33416.execute-api.us-east-2.amazonaws.com/test/analytics/past_predictions";


    private HttpResponse<String> requestDataToEndpoint(String course, String endpoint) throws IOException, InterruptedException {
        var client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(endpoint + "?course=" + course))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public PredictionResponse getPrediction (String course) throws IOException, InterruptedException {
        HttpResponse<String> response = requestDataToEndpoint(course, URL_PREDICT);
        var mapper = new ObjectMapper();
        return mapper.readValue(response.body(), new TypeReference<>() {});
    }


    public List<ReportData> getReport(String course) throws IOException, InterruptedException {
        HttpResponse<String> response = requestDataToEndpoint(course, URL_REPORT);
        var mapper = new ObjectMapper();
        System.out.println(response.body());
        return mapper.readValue(response.body(), new TypeReference<>() {});
    }
}
