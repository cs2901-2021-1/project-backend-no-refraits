package com.example.projectbackendnorefraits;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectBackendNoRefraitsApplicationTest {

    @Test
    void mainCompiles() throws InterruptedException {
        Thread t = new Thread(() -> ProjectBackendNoRefraitsApplication.main(new String[]{}));
        t.start();
        t.join(5000);
        assertTrue(t.isAlive());
    }
}