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
@CrossOrigin(origins = "*", maxAge = 3600)
public class UsuarioController {
    static final String CLIENT_URL = "*";

    private final UsuarioService usuarioService;

    private final AuthenticationService authenticationService;

    private final JwtTokenUtil jwtTokenUtil;

    private final RolService rolService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, AuthenticationService authenticationService, JwtTokenUtil jwtTokenUtil, RolService rolService) {
        this.usuarioService = usuarioService;
        this.authenticationService = authenticationService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.rolService = rolService;
    }

    //POST
    @PostMapping("/create/{rol}/{direccion}")
    public Usuario newUsuario(@PathVariable String rol, @PathVariable String direccion, @RequestBody Usuario usuario, @RequestHeader("Authorization") String token) {
        var itexists = usuarioService.existsByEmail(usuario.getEmail());
        if (itexists) return null;
        var username = jwtTokenUtil.getUsernameFromToken(token);
        var user = usuarioService.findUsuarioByEmailAndNombreNotNull(username);
        if(Boolean.TRUE.equals(usuarioService.isSysAdmin(user))) {
            var newrol = rolService.findOneByName(rol);
            usuario.setRol(newrol);
            usuario.setGoogleid("");
            usuario.setNombre("");
            usuario.setDireccion(direccion);
            return authenticationService.save(usuario);
        }

        var newrol = rolService.findOneByName("DIR_USER");
        usuario.setRol(newrol);
        usuario.setGoogleid("");
        usuario.setNombre("");
        usuario.setDireccion(user.getDireccion());
        return authenticationService.save(usuario);
    }

    //GET ALL
    @GetMapping("/getall")
    public List<UsuarioDisplay> readAll(@RequestHeader("Authorization") String token) {
        var username = jwtTokenUtil.getUsernameFromToken(token);
        var user = usuarioService.findUsuarioByEmailAndNombreNotNull(username);
        if(Boolean.TRUE.equals(usuarioService.isSysAdmin(user))) {
           return usuarioService.getAllUserToDisplay(username);
        }
        return usuarioService.getUsersUnderDirection(user.getDireccion(), username);

    }

    //GET by ID
    @GetMapping("/{id}")
    public Usuario one(@PathVariable Long id) {
        return usuarioService.findOne(id);
    }


    //DELETE by ID
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        usuarioService.deleteById(id);
    }

}