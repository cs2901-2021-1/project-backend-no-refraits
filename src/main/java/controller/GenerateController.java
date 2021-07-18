package controller;
import business.AuthenticationService;
import business.RolService;
import business.UsuarioService;
import data.entities.Rol;
import data.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/test")
public class GenerateController {
    static final String CLIENT_URL = "*";
    static final String SYSADMINUSER = "SYS_ADMIN";
    static final String DIRADMINUSER = "DIR_ADMIN";
    static final String DGAUSER = "DIR_DGA";
    static final String DIRUSER = "DIR_USER";

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationService userService;

    @Autowired
    private RolService rolService;

    @GetMapping(value = "/generateroles")
    public String generateroles() {
        createRoles();
        return "OK";
    }

    @GetMapping(value = "/generateusers")
    public String generateusers() {
        createSysAdminUser("renato.rodriguez.l@utec.edu.pe", SYSADMINUSER);
        return "OK";
    }

    public String createRoles(){
        rolService.create(new Rol(SYSADMINUSER));
        rolService.create(new Rol(DIRADMINUSER));
        rolService.create(new Rol(DGAUSER));
        rolService.create(new Rol(DIRUSER));
        return "OK";
    }

    public void createSysAdminUser(String gmail, String rol){
        var user = new Usuario();
        var roleAdmin = rolService.findOneByName(rol);
        user.setRol(roleAdmin);
        user.setDireccion("");
        user.setEmail(gmail);
        user.setGoogleid("");
        user.setNombre("");
        userService.save(user);

    }

    public void createUser(String gmail,String rol, String direccion) {
        var user = new Usuario();
        var roleAdmin = rolService.findOneByName(rol);
        user.setRol(roleAdmin);
        user.setDireccion(direccion);
        user.setEmail(gmail);
        user.setGoogleid("");
        user.setNombre("");
        userService.save(user);
    }
}
