package business;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Service
@Transactional
@Slf4j
public class UtecService {
    public static final String COURSES_ENDPOINT = "https://b8acm33416.execute-api.us-east-2.amazonaws.com/test/aux_db/courses_all?cod_direction=";
    public static final String DIRECTION_ENDPOINT = "https://b8acm33416.execute-api.us-east-2.amazonaws.com/test/aux_db/directions_all";

    private HttpResponse<String> requestDataToEndpoint(String endpoint) throws IOException, InterruptedException {
        log.info(endpoint);
        var client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(endpoint))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public List<Map<String, String>> getAllDirections() throws IOException, InterruptedException {
        HttpResponse<String> response = requestDataToEndpoint(DIRECTION_ENDPOINT);
        var mapper = new ObjectMapper();
        return mapper.readValue(response.body(), new TypeReference<>() {});
    }

    public List<Map<String, String>> getCourseFromDireccion(String direccion) throws IOException, InterruptedException {
        log.warn(direccion);
        HttpResponse<String> response = requestDataToEndpoint(COURSES_ENDPOINT + direccion);
        var mapper = new ObjectMapper();
        return mapper.readValue(response.body(), new TypeReference<>() {});
    }
}
