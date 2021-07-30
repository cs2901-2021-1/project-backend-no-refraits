package com.example.projectbackendnorefraits;

import business.AIService;
import com.jayway.jsonpath.JsonPath;
import data.entities.PredictionRequest;
import data.entities.PredictionResponse;
import data.entities.ReportData;
import functions.LoginFunctions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.logging.Logger;

@SpringBootTest(classes = ProjectBackendNoRefraitsApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AITest {
    static final Logger logger = Logger.getLogger(AITest.class.getName());

    @Autowired
    MockMvc mvc;

    @Autowired
    AIService service;

    static final String TEST_COURSE = "ME0001";
    static final String PREDICTION_ENDPOINT = "/ai/prediccion";
    static final String REPORT_ENDPOINT = "/ai/report";

    @Test
    @Order(1)
    void predictionEndpointIsReachable() {
        Assertions.assertDoesNotThrow(() -> {
            PredictionResponse response = service.getPrediction(TEST_COURSE);
            logger.fine(() -> "" + response.getPrediction() + " " + response.getFiability());
        });
    }

    @Test
    @Order(1)
    void reportEndpointIsReachable() {
        Assertions.assertDoesNotThrow(() -> {
            List<ReportData> response = service.getReport(TEST_COURSE);
            logger.fine(response::toString);
        });
    }


    @Test
    @Order(2)
    void predictionWorksCorrectly() throws Exception {
        try { predictionEndpointIsReachable(); } catch (Exception e) { Assumptions.assumeTrue(false); }

        final MvcResult result = LoginFunctions.simulateLogin(mvc);
        final String response = result.getResponse().getContentAsString();
        final String token = JsonPath.parse(response).read("$[\"result\"][\"token\"]");

        mvc.perform(MockMvcRequestBuilders.post(PREDICTION_ENDPOINT)
                .header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,PATCH,OPTIONS")
                .header("Authorization", token)
                .contentType("application/json")
                .content(LoginFunctions.asJsonString(new PredictionRequest(TEST_COURSE))))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        Assertions.assertTrue(true);
    }

    @Test
    @Order(2)
    void reportWorksCorrectly() throws Exception {
        try { reportEndpointIsReachable(); } catch (Exception e) { Assumptions.assumeTrue(false); }

        final MvcResult result = LoginFunctions.simulateLogin(mvc);
        final String response = result.getResponse().getContentAsString();
        final String token = JsonPath.parse(response).read("$[\"result\"][\"token\"]");

        mvc.perform(MockMvcRequestBuilders.post(REPORT_ENDPOINT)
                .header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,PATCH,OPTIONS")
                .header("Authorization", token)
                .contentType("application/json")
                .content(LoginFunctions.asJsonString(new PredictionRequest(TEST_COURSE))))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        Assertions.assertTrue(true);
    }

    @Test
    @Timeout(value = 10)
    void testTimeout () throws  Exception {
        predictionWorksCorrectly();
        reportWorksCorrectly();
        Assertions.assertTrue(true);
    }

}
