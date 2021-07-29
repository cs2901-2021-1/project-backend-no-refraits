package controller;


import business.AIService;
import data.entities.PredictionRequest;
import data.entities.PredictionResponse;
import data.entities.ReportData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/ai")
public class AIController {
    static final String CLIENT_URL = "*";
    final AIService aiService;

    @Autowired
    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/prediccion")
    public PredictionResponse prediccion(@RequestBody PredictionRequest request) throws IOException, InterruptedException {
        return aiService.getPrediction(request.getCurso());
    }


    @PostMapping("/report")
    public List<ReportData> report(@RequestBody PredictionRequest request) throws IOException, InterruptedException {
        return aiService.getReport(request.getCurso());
    }
}
