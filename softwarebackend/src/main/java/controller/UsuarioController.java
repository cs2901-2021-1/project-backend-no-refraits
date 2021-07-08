package controller;

import business.AuthenticationUserService;
import business.UsuarioService;
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

    @GetMapping
    @CrossOrigin(origins = clientUrl)
    public List<Usuario> readAll() { return service.findAll(); }

    @PostMapping
    @CrossOrigin(origins = clientUrl)
    Usuario newUsuario(@RequestBody Usuario newUsuario) { return authenticationUserService.save(newUsuario); }

    @GetMapping("/{id}")
    @CrossOrigin(origins = clientUrl)
    public Usuario one(@PathVariable Long id) { return service.findOne(id); }

    @PutMapping("/{id}")
    @CrossOrigin(origins = clientUrl)
    public Usuario replaceTarea(@RequestBody Usuario newUsuario, @PathVariable Long id) { return service.update(newUsuario, id); }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = clientUrl)
    void deleteTarea(@PathVariable Long id) { service.deleteById(id); }

}