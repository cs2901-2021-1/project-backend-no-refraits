package controller;

import business.AuthenticationUserService;
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
    final static String clientUrl = "*";

    @Autowired
    private UsuarioService service;

    @Autowired
    private AuthenticationUserService authenticationUserService;

    @Autowired
    private  JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RolService rolService;

    //POST
    @PostMapping("/create/{rol}/{direccion}")
    @CrossOrigin(origins = clientUrl)
    public Usuario newUsuario(@PathVariable String rol, @PathVariable String direccion, @RequestBody Usuario nuevousuario, @RequestHeader("Authorization") String token) {
        System.out.println("dqwdwqdqdwq");
        var username = jwtTokenUtil.getUsernameFromToken(token);
        var user = service.findUsuarioByEmailAndNombreNotNull(username);
        if(service.isSysAdmin(user)){
            System.out.println("dddddddddddddddddddddddddddd");
            var newrol = rolService.findOneByName(rol);
            nuevousuario.setRol(newrol);
            nuevousuario.setGoogleid("");
            nuevousuario.setNombre("");
            nuevousuario.setDireccion(direccion);
            return authenticationUserService.save(nuevousuario);
        }
        System.out.println("aaaaaaaaaaaaaaaaaaa");

        var newrol = rolService.findOneByName("DIR_USER");
        nuevousuario.setRol(newrol);
        nuevousuario.setGoogleid("");
        nuevousuario.setNombre("");
        System.out.println(user.getDireccion());
        nuevousuario.setDireccion(user.getDireccion());
        return authenticationUserService.save(nuevousuario);
    }

    //GET ALL
    @GetMapping("/getall")
    @CrossOrigin(origins = clientUrl)
    public List<UsuarioDisplay> readAll(@RequestHeader("Authorization") String token) {
        var username = jwtTokenUtil.getUsernameFromToken(token);
        var user = service.findOneByEmail(username);
        return service.getAllUsertoDisplay(user.getDireccion());
    }

    //GET by ID
    @GetMapping("/{id}")
    @CrossOrigin(origins = clientUrl)
    public Usuario one(@PathVariable Long id, @RequestBody AuthData authData) { return service.findOne(id); }


    //UPDATE by ID
    @PutMapping("/{id}")
    @CrossOrigin(origins = clientUrl)
    public Usuario replaceTarea(@RequestBody Usuario newUsuario, @PathVariable Long id) { return service.update(newUsuario, id); }

    //DELETE by ID
    @DeleteMapping("/{id}")
    @CrossOrigin(origins = clientUrl)
    void deleteTarea(@PathVariable Long id) { service.deleteById(id); }

}