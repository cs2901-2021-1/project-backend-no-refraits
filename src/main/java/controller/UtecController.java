package controller;

import business.UtecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.*;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/testc")
public class UtecController {

    private final UtecService utecService;

    @Autowired
    UtecController(UtecService utecService) {
        this.utecService = utecService;
    }

    // temporary function
    @GetMapping(value="/directions")
    public List<Map<String, String>> getDirections() throws SQLException {
        return utecService.getAllDirections();
    }

}
