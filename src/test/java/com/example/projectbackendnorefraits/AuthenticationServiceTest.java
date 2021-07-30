package com.example.projectbackendnorefraits;

import business.AuthenticationService;
import data.entities.Usuario;
import data.repositories.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@SpringBootTest(classes = ProjectBackendNoRefraitsApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthenticationServiceTest {
    @Autowired
    AuthenticationService service;

    @MockBean
    UsuarioRepository repository;

    @Test
    void loadUserByUsernameNotFound() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("knknknkn"));
    }


    @Test
    void save() {
        var user = new Usuario();
        user.setEmail(UserFunctionsTest.TEST_GMAIL);
        user.setGoogleid(UserFunctionsTest.TEST_GID);
        Mockito.when(repository.save(user)).thenReturn(user);
        Assertions.assertSame(service.save(user), user);
    }

}