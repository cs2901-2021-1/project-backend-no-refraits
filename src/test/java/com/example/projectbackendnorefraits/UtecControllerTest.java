package com.example.projectbackendnorefraits;

import business.UtecService;
import com.jayway.jsonpath.JsonPath;
import functions.LoginFunctions;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
class UtecControllerTest {
    @Autowired
    UtecService service;

    @Autowired
    MockMvc mvc;

    @Test
    void setupIsCorrect () {
        Assertions.assertNotNull(UtecService.user);
        Assertions.assertNotNull(UtecService.password);
        Assertions.assertNotNull(UtecService.url);

        Assertions.assertDoesNotThrow(() -> service.getConnection());
    }

    @Test
    void getDirections() throws Exception {
        final MvcResult result = LoginFunctions.simulateLogin(mvc);
        final String response = result.getResponse().getContentAsString();
        final String token = JsonPath.parse(response).read("$[\"result\"][\"token\"]");


         mvc.perform(MockMvcRequestBuilders.get("/testc/directions")
                .header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,PATCH,OPTIONS")
                .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());

         Assertions.assertTrue(true);
    }


    @Test
    void getCourses() throws Exception {
        final MvcResult result = LoginFunctions.simulateLogin(mvc);
        final String response = result.getResponse().getContentAsString();
        final String token = JsonPath.parse(response).read("$[\"result\"][\"token\"]");


        mvc.perform(MockMvcRequestBuilders.get("/testc/directions")
                .header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,PATCH,OPTIONS")
                .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());

        Assertions.assertTrue(true);
    }
}