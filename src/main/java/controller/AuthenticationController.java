package controller;


import business.AuthenticationService;
import business.UsuarioService;
import config.JwtTokenUtil;
import data.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/token")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final UsuarioService usuarioService;

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UsuarioService usuarioService, AuthenticationService authenticationService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.usuarioService = usuarioService;
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/generate-token")
    public Response<AuthData> register(@RequestBody Login loginUser) throws AuthenticationException {
        final var user = usuarioService.findOneByEmail(loginUser.getEmail());
        if (user == null)
        {
            return new Response<>(404, "No existe este usuario", null);
        }
        if(user.getNombre().equals("")){
            var newUser = new Usuario(loginUser.getEmail(), loginUser.getNombre(),loginUser.getPassword(), user.getDireccion(),user.getRol());
            usuarioService.update(newUser, user.getId());
            authenticationService.updatePassword(user.getId());
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword()));
        final String token = jwtTokenUtil.generateToken(user);
        return new Response<>(200, "success",
                new AuthData(token, user.getEmail(), usuarioService.getPrettyNameRolebyId(user.getRol().getId())));
    }

    @GetMapping(value = "/update-password")
    public String updatePasswordUser(){
        Usuario u = usuarioService.findOneByEmail("jbellido@uc.cl");
        authenticationService.updatePassword(u.getId());
        return "OK";
    }
    @GetMapping(value="/checkiflogged")
    public boolean checkiflogged(@RequestHeader("Authorization") String token){
        var username = jwtTokenUtil.getUsernameFromToken(token);
        if (username == null){
            return false;
        }
        return !Boolean.TRUE.equals(jwtTokenUtil.isTokenExpired(token));
    }
    @GetMapping(value="/validatetypefofuser/{option}")
    public boolean validatetypefofuser(@RequestHeader("Authorization") String token, @PathVariable int option){
        var username = jwtTokenUtil.getUsernameFromToken(token);
        if(username == null){
            return false;
        }
        var check = false;
        var user = usuarioService.findOneByEmail(username);
        Long rolId = user.getRol().getId();
        switch (option){
            case 1: //If user is sysadmin
                check = (rolId==1);
                break;

            case 2: //If user is diradmin
                check = (rolId==2);
                break;

            case 3: //if user is dga
                check = (rolId==3);
                break;

            case 4: //If user is diruser
                check = (rolId==4);
                break;

            default:  //If is any kind of admin
                check = (rolId==1 || rolId ==2);
                break;
        }
        return check;
    }


    @GetMapping(value="/checkifuser")
    public Boolean checkifuser(@RequestHeader("Authorization") String token){
        var username = jwtTokenUtil.getUsernameFromToken(token);
        return username != null;
    }

    @GetMapping(value="/checkifdiruser")
    public Boolean checkifdiruser(@RequestHeader("Authorization") String token ){
        var username = jwtTokenUtil.getUsernameFromToken(token);
        var user = usuarioService.findOneByEmail(username);
        return (user.getRol().getId() == 4);
    }

    @GetMapping(value="/checkifdgauser")
    public Boolean checkifdgauser(@RequestHeader("Authorization") String token){
        var username = jwtTokenUtil.getUsernameFromToken(token);
        var user = usuarioService.findOneByEmail(username);
        return (user.getRol().getId() == 3);
    }

    @GetMapping(value="/checkifadminuser")
    public Boolean checkifadminuser(@RequestHeader("Authorization") String token ){
        var username = jwtTokenUtil.getUsernameFromToken(token);
        var user = usuarioService.findOneByEmail(username);
        return (user.getRol().getId() == 2 || user.getRol().getId() == 1);
    }

    @GetMapping(value="/checkifadmindiruser")
    public Boolean checkifadmindiruser(@RequestHeader("Authorization") String token ){
        var username = jwtTokenUtil.getUsernameFromToken(token);
        var user = usuarioService.findOneByEmail(username);
        return (user.getRol().getId() == 2);
    }

    @GetMapping(value="/checkifuserbelongstodirec")
    public Boolean checkifuserbelongstodirec(@RequestHeader("Authorization") String token ){
        var username = jwtTokenUtil.getUsernameFromToken(token);
        var user = usuarioService.findOneByEmail(username);
        return (user.getRol().getId() == 3 || user.getRol().getId() == 4);
    }


    @GetMapping(value="/checkifsysadminuser")
    public Boolean checkifsysadminuser(@RequestHeader("Authorization") String token ){
        var username = jwtTokenUtil.getUsernameFromToken(token);
        var user = usuarioService.findOneByEmail(username);
        return (user.getRol().getId() == 1);
    }

}
