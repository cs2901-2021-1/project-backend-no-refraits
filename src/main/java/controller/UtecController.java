package controller;

import business.UsuarioService;
import business.UtecService;
import config.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @GetMapping(value="/directions")
    public List<Map<String, String>> getDirections() throws IOException, InterruptedException {
        return utecService.getAllDirections();
    }

    @GetMapping(value = "/cursos/{direccionId}")
    public List<Map<String, String>> getCourses(@PathVariable String direccionId, @RequestHeader("Authorization") String token) throws IOException, InterruptedException {
        var username = jwtTokenUtil.getUsernameFromToken(token);
        var user = usuarioService.findUsuarioByEmailAndNombreNotNull(username);
        if(user.getRol().getId() == 2 || user.getRol().getId() == 4){
            return utecService.getCourseFromDireccion(user.getDireccion());
        }
        return utecService.getCourseFromDireccion(direccionId);
    }

}
