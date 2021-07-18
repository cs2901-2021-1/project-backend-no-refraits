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

    @Autowired
    private UsuarioService service;

    @Autowired
    private AuthenticationService authenticationUserService;

    @Autowired
    private  JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RolService rolService;

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
            return authenticationUserService.save(nuevousuario);
        }

        var newrol = rolService.findOneByName("DIR_USER");
        nuevousuario.setRol(newrol);
        nuevousuario.setGoogleid("");
        nuevousuario.setNombre("");
        nuevousuario.setDireccion(user.getDireccion());
        return authenticationUserService.save(nuevousuario);
    }

    //GET ALL
    @GetMapping("/getall")
    @CrossOrigin(origins = CLIENT_URL)
    public List<Usuario> readAll(@RequestHeader("Authorization") String token) {
        var username = jwtTokenUtil.getUsernameFromToken(token);
        var user = service.findUsuarioByEmailAndNombreNotNull(username);
        List<Usuario> retorno;
        if(Boolean.TRUE.equals(service.isSysAdmin(user))) {
            retorno = service.getAllUserToDisplay(username);
        }
        else {
            retorno = service.getUsersUnderDirection(user.getDireccion(), username);
        }
        return retorno;
    }

    //GET by ID
    @GetMapping("/{id}")
    @CrossOrigin(origins = CLIENT_URL)
    public Usuario one(@PathVariable Long id, @RequestBody AuthData authData) { return service.findOne(id); }


    //DELETE by ID
    @DeleteMapping("/delete/{id}")
    @CrossOrigin(origins = CLIENT_URL)
    public void deleteUser(@PathVariable Long id) {
        service.deleteById(id);
    }

}