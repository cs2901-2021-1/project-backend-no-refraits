package controller;

import business.AuthenticationService;
import business.RolService;
import business.UsuarioService;
import config.JwtTokenUtil;
import data.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
//@CrossOrigin(origins = "*")
public class UsuarioController {
    static final String CLIENT_URL = "*";

    private final UsuarioService service;

    private final AuthenticationService authenticationService;

    private final JwtTokenUtil jwtTokenUtil;

    private final RolService rolService;

    @Autowired
    public UsuarioController(UsuarioService service, AuthenticationService authenticationService, JwtTokenUtil jwtTokenUtil, RolService rolService) {
        this.service = service;
        this.authenticationService = authenticationService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.rolService = rolService;
    }



    //POST
    @PostMapping("/create/{rol}/{direccion}")
    @CrossOrigin(origins = CLIENT_URL)
    public Usuario newUsuario(@PathVariable String rol, @PathVariable String direccion, @RequestBody Usuario nuevousuario, @RequestHeader("Authorization") String token) {
        var username = jwtTokenUtil.getUsernameFromToken(token);
        var user = service.findUsuarioByEmailAndNombreNotNull(username);
        if(Boolean.TRUE.equals(service.isSysAdmin(user))) {
            var newrol = rolService.findOneByName(rol);
            nuevousuario.setRol(newrol);
            nuevousuario.setGoogleid("");
            nuevousuario.setNombre("");
            nuevousuario.setDireccion(direccion);
            return authenticationService.save(nuevousuario);
        }

        var newrol = rolService.findOneByName("DIR_USER");
        nuevousuario.setRol(newrol);
        nuevousuario.setGoogleid("");
        nuevousuario.setNombre("");
        nuevousuario.setDireccion(user.getDireccion());
        return authenticationService.save(nuevousuario);
    }

    //GET ALL
    @GetMapping("/getall")
    @CrossOrigin(origins = CLIENT_URL)
    public List<UsuarioDisplay> readAll(@RequestHeader("Authorization") String token) {
        var username = jwtTokenUtil.getUsernameFromToken(token);
        var user = service.findUsuarioByEmailAndNombreNotNull(username);
        if(Boolean.TRUE.equals(service.isSysAdmin(user))) {
           return service.getAllUserToDisplay(username);
        }
        return service.getUsersUnderDirection(user.getDireccion(), username);

    }

    //GET by ID
    @GetMapping("/{id}")
    @CrossOrigin(origins = CLIENT_URL)
    public Usuario one(@PathVariable Long id) {
        return service.findOne(id);
    }


    //DELETE by ID
    @DeleteMapping("/delete/{id}")
    @CrossOrigin(origins = CLIENT_URL)
    public void deleteUser(@PathVariable Long id) {
        service.deleteById(id);
    }

}