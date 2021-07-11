package controller;

import business.AuthenticationUserService;
import business.UsuarioService;
import config.JwtTokenUtil;
import data.entities.AuthToken;
import data.entities.Usuario;
import data.entities.UsuarioDisplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    //POST
    @PostMapping
    @CrossOrigin(origins = clientUrl)
    public Usuario newUsuario(@RequestBody Usuario usuario) {
        return authenticationUserService.save(usuario);
    }

    //GET ALL
    @GetMapping("/getall/{direccion}")
    @CrossOrigin(origins = clientUrl)
    public List<UsuarioDisplay> readAll(@PathVariable String direccion) {
        System.out.println(service.findAll().size());
        return service.getAllUsertoDisplay(direccion);
    }


    //GET by ID
    @GetMapping("/{id}")
    @CrossOrigin(origins = clientUrl)
    public Usuario one(@PathVariable Long id, @RequestBody AuthToken authToken ) { return service.findOne(id); }



    //UPDATE by ID
    @PutMapping("/{id}")
    @CrossOrigin(origins = clientUrl)
    public Usuario replaceTarea(@RequestBody Usuario newUsuario, @PathVariable Long id) { return service.update(newUsuario, id); }

    //DELETE by ID
    @DeleteMapping("/{id}")
    @CrossOrigin(origins = clientUrl)
    void deleteTarea(@PathVariable Long id, @RequestBody AuthToken authToken ) { service.deleteById(id); }

}