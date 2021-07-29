package com.example.projectbackendnorefraits;

import functions.LoginFunctions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.*;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoginTests {

    @Autowired
    private MockMvc mvc;

    public final String[] createUserTests = {
            "/usuarios/create/SYS_ADMIN/81",
            "/usuarios/create/",
            "/usuarios/create/bad/args"
    };

    public final String[] getAllUserTests = {
            "/usuarios/getall"
    };

    public final String[] getOneUserTests = {
            "/usuarios/1",
            "/usuarios/898989898",
            "/usuarios/badarg",
    };

    public final String[] deleteOneTests = {
            "/usuarios/delete/1",
            "/usuarios/delete/898989898",
            "/usuarios/delete/badarg",
    };

    public final String[][] userTestUrls = {
            createUserTests,
            getAllUserTests,
            getOneUserTests,
            deleteOneTests,
    };

    @Test
    @Order(1)
    void unloggedUserRejectedFromRestrictedViews() throws Exception {
        for (var testSet : userTestUrls) {
            var testUrl = testSet[0];
            mvc.perform(MockMvcRequestBuilders.post(testUrl))
                    .andExpect(MockMvcResultMatchers.status().isUnauthorized());
        }
    }


    @Test
    @Order(2)
    void loginAllowedUserReturnsCode200AndToken() throws Exception {
        final MvcResult result = LoginFunctions.simulateLogin(mvc);
        final String response = result
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
        final MvcResult result = LoginFunctions.simulateLogin(mvc);
        final String response = result.getResponse().getContentAsString();
        final String token = JsonPath.parse(response).read("$[\"result\"][\"token\"]");

        mvc.perform(MockMvcRequestBuilders.get("/authorization/checkiflogged")
                .header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,PATCH,OPTIONS")
                .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    @Order(3)
    void nonExistentUserRejectedOnLogin() throws Exception {
        final MvcResult result = LoginFunctions.simulateLoginFail(mvc);
        final String response = result
                .getResponse()
                .getContentAsString();

        final Integer status = JsonPath.parse(response).read("$[\"status\"]");
        Assertions.assertEquals(404, status);
    }

    @Test
    @Order(4)
    void invalidTokenIsRejected() throws Exception {
        final String falseToken = "this.isa.falsetoken";
        mvc.perform(MockMvcRequestBuilders.get("/authorization/checkiflogged")
                .header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,PATCH,OPTIONS")
                .header("Authorization", falseToken))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());

    }
}