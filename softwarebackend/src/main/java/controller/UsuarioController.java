package controller;

import business.AuthenticationUserService;
import business.UsuarioService;
import data.entities.AuthToken;
import data.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
    final static String clientUrl = "*";

    @Autowired
    private UsuarioService service;

    @Autowired
    private AuthenticationUserService authenticationUserService;

    //POST
    @PostMapping
    @CrossOrigin(origins = clientUrl)
    Usuario newUsuario(@RequestBody Usuario newUsuario, @RequestBody AuthToken authToken ) {
        
        return authenticationUserService.save(newUsuario);
    }

    //GET ALL
    @GetMapping
    @CrossOrigin(origins = clientUrl)
    public List<Usuario> readAll(@RequestBody AuthToken authToken) { return service.findAll(); }

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