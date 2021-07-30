package controller;

import business.UsuarioService;
import business.UtecService;
import config.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.http.HTTPException;
import java.net.http.HttpResponse;
import java.sql.*;
import java.util.*;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/testc")
public class UtecController {

    private final UtecService utecService;

    private final UsuarioService usuarioService;

    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    UtecController(UtecService utecService, JwtTokenUtil jwtTokenUtil, UsuarioService usuarioService)
    {
        this.utecService = utecService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.usuarioService = usuarioService;
    }

    // temporary function
    @GetMapping(value="/directions")
    public List<Map<String, String>> getDirections() throws SQLException {
        try {
            return utecService.getAllDirections();
        } catch (Exception e) {
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ArrayList<>(Collections.singletonList(map));
        }
    }

    @GetMapping(value = "/cursos/{direccionId}")
    public List<Map<String, String>> getCourses(@PathVariable String direccionId, @RequestHeader("Authorization") String token) throws SQLException{
        var username = jwtTokenUtil.getUsernameFromToken(token);
        var user = usuarioService.findUsuarioByEmailAndNombreNotNull(username);
        if(user.getRol().getId() == 3 || user.getRol().getId() == 4){
            return utecService.getCourseFromDireccion(user.getDireccion());
        }
        return utecService.getCourseFromDireccion(direccionId);
    }

}
