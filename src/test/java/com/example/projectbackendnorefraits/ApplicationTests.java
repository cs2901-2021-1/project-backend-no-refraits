package com.example.projectbackendnorefraits;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import data.entities.Login;
import org.junit.jupiter.api.*;
import org.springframework.test.web.servlet.MvcResult;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApplicationTests {

    @Autowired
    private MockMvc mvc;

    public final String[] createTests = {
            "/usuarios/create/SYS_ADMIN/Dirección de Ciencias",
            "/usuarios/create/",
            "/usuarios/create/bad/args"
    };

    public final String[] getAllTests = {
            "/usuarios/getall"
    };

    public final String[] getOneTests = {
            "/usuarios/1",
            "/usuarios/898989898",
            "/usuarios/badarg",
    };

    public final String[] deleteOneTests = {
            "/usuarios/delete/1",
            "/usuarios/delete/898989898",
            "/usuarios/delete/badarg",
    };

    public final String[][] testUrls = {
            createTests,
            getAllTests,
            getOneTests,
            deleteOneTests,
    };

    @Test
    @Order(1)
    void unloggedUserRejectedFromRestrictedViews() throws Exception {
        for (var testSet : testUrls) {
            var testUrl = testSet[0];
            mvc.perform(post(testUrl))
                    .andExpect(status().isUnauthorized());
        }
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    Login getLoginMockData() {
        return new Login("esteban.villacorta@utec.edu.pe", "111014891633982592683");
    }

    Login fakeLoginMockData() {
        return new Login("absolutely.fake@fake.edu.pe", "111014891633982592683");
    }
    
    MvcResult simulateLogin() throws Exception {
        final var login = getLoginMockData();
        return mvc.perform(post("/token/generate-token")
                .content(asJsonString(login))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    MvcResult simulateLoginFail() throws Exception {
        final var login = fakeLoginMockData();
        return mvc.perform(post("/token/generate-token")
                .content(asJsonString(login))
                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().is4xxClientError())
                .andReturn();
    }
    
    @Test
    @Order(2)
    void loginAllowedUserReturnsCode200AndToken() throws Exception {
        final MvcResult simulateLogin = simulateLogin();
        final String response = simulateLogin
                .getResponse()
                .getContentAsString();
        final String token = JsonPath.parse(response).read("$[\"result\"][\"token\"]");
        final Integer status = JsonPath.parse(response).read("$[\"status\"]");
        Assertions.assertEquals(200, status);
        Assertions.assertNotNull(token);
    }

    @Test
    @Order(2)
    void userIsLoggedAfterSendingLoginData() throws Exception {
        final MvcResult result = simulateLogin();
        final String response = result.getResponse().getContentAsString();
        final String token = JsonPath.parse(response).read("$[\"result\"][\"token\"]");

        mvc.perform(get("/token/checkiflogged")
                .header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,PATCH,OPTIONS")
                .header("Authorization", token))
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("true"));
    }

    @Test
    @Order(3)
    void nonExistentUserRejectedOnLogin() throws Exception {
        final MvcResult simulateLogin = simulateLoginFail();
        final String response = simulateLogin
                .getResponse()
                .getContentAsString();

        final Integer status = JsonPath.parse(response).read("$[\"status\"]");
        Assertions.assertEquals(404, status);
    }

    @Test
    @Order(4)
    void invalidTokenIsRejected() throws Exception {
        final String falseToken = "this.isa.falsetoken";
        mvc.perform(get("/token/checkiflogged")
                .header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,PATCH,OPTIONS")
                .header("Authorization", falseToken))
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("false"));

    }
}