package com.example.projectbackendnorefraits;

import oracle.ucp.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import data.entities.*;
import data.repositories.*;

import controller.*;
import business.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.*;
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

    @MockBean
    @Autowired
    private UsuarioService userService;


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

}
