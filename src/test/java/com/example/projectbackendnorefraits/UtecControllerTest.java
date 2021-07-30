package com.example.projectbackendnorefraits;

import business.UtecService;
import com.jayway.jsonpath.JsonPath;
import controller.UtecController;
import functions.LoginFunctions;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
class UtecControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    UtecController controller;


    @Test
    void getDirections() throws Exception {
        List<Map<String, String>> testalldirections = controller.getDirections();

        final MvcResult result = LoginFunctions.simulateLogin(mvc);
        final String response = result.getResponse().getContentAsString();
        final String token = JsonPath.parse(response).read("$[\"result\"][\"token\"]");


        MvcResult directionsResult = mvc.perform(MockMvcRequestBuilders.get("/testc/directions")
                .header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,PATCH,OPTIONS")
                .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        var strResult = directionsResult.getResponse().getContentAsString();
        final List<Map<String, String>> allDirections = JsonPath.parse(strResult).json();

        Assertions.assertNotNull(allDirections);
        Assertions.assertFalse(allDirections.isEmpty());
        Assertions.assertEquals(testalldirections, allDirections);
    }


    @Test
    void getCourses() throws Exception {
        final MvcResult result = LoginFunctions.simulateLogin(mvc);
        final String response = result.getResponse().getContentAsString();
        final String token = JsonPath.parse(response).read("$[\"result\"][\"token\"]");


        MvcResult courseResult = mvc.perform(MockMvcRequestBuilders.get("/testc/directions")
                .header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,PATCH,OPTIONS")
                .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        var strResult = courseResult.getResponse().getContentAsString();
        final List<Map<String, String>> courses = JsonPath.parse(strResult).json();

        Assertions.assertNotNull(courses);
        Assertions.assertFalse(courses.isEmpty());
    }

    @Test
    void handle50RequestsAtTheTime() throws Exception {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 50; ++i) {
            threads.add(new Thread(() -> Assertions.assertDoesNotThrow(this::getCourses)));
        }
        for (var thread : threads) thread.start();
        for (var thread : threads) thread.join();
    }
}