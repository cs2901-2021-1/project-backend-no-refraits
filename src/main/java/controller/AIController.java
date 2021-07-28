package controller;


import business.AIService;
import data.entities.PredictionRequest;
import data.entities.PredictionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/ai")
public class AIController {
    static final String CLIENT_URL = "*";
    final AIService aiService;

    @Autowired
    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/prediccion")
    @CrossOrigin(origins = CLIENT_URL)
    public PredictionResponse prediccion(@RequestBody PredictionRequest request) throws URISyntaxException, IOException, InterruptedException {
        return aiService.getPrediction(request.getCurso());
    }

}
