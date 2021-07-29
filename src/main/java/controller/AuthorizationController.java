package controller;

import business.UsuarioService;
import config.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/authorization")
public class AuthorizationController {
    private final JwtTokenUtil jwtTokenUtil;
    private final UsuarioService usuarioService;

    @Autowired
    public AuthorizationController(JwtTokenUtil jwtTokenUtil, UsuarioService usuarioService){
        this.jwtTokenUtil = jwtTokenUtil;
        this.usuarioService = usuarioService;
    }

    @GetMapping(value="/checkiflogged")
    public boolean checkiflogged(@RequestHeader("Authorization") String token){

        if (!checkifuser(token))
            return false;
        return !Boolean.TRUE.equals(jwtTokenUtil.isTokenExpired(token));
    }

    @GetMapping(value="/checkifuser")
    public boolean checkifuser(@RequestHeader("Authorization") String token){
        try {
            var username = jwtTokenUtil.getUsernameFromToken(token);
            return username != null;
        } catch (Exception e) {
            return false;
        }
    }

    @GetMapping(value="/checkifdiruser")
    public boolean checkifdiruser(@RequestHeader("Authorization") String token ){
        var username = jwtTokenUtil.getUsernameFromToken(token);
        var user = usuarioService.findOneByEmail(username);
        return (user.getRol().getId() == 4);
    }

    @GetMapping(value="/checkifdgauser")
    public boolean checkifdgauser(@RequestHeader("Authorization") String token){
        var username = jwtTokenUtil.getUsernameFromToken(token);
        var user = usuarioService.findOneByEmail(username);
        return (user.getRol().getId() == 3);
    }

    @GetMapping(value="/checkifadminuser")
    public boolean checkifadminuser(@RequestHeader("Authorization") String token ){
        var username = jwtTokenUtil.getUsernameFromToken(token);
        var user = usuarioService.findOneByEmail(username);
        return (user.getRol().getId() == 2 || user.getRol().getId() == 1);
    }

    @GetMapping(value="/checkifadmindiruser")
    public boolean checkifadmindiruser(@RequestHeader("Authorization") String token ){
        var username = jwtTokenUtil.getUsernameFromToken(token);
        var user = usuarioService.findOneByEmail(username);
        return (user.getRol().getId() == 2);
    }

    @GetMapping(value="/checkifuserbelongstodirec")
    public boolean checkifuserbelongstodirec(@RequestHeader("Authorization") String token ){
        var username = jwtTokenUtil.getUsernameFromToken(token);
        var user = usuarioService.findOneByEmail(username);
        return (user.getRol().getId() == 3 || user.getRol().getId() == 4);
    }


    @GetMapping(value="/checkifsysadminuser")
    public boolean checkifsysadminuser(@RequestHeader("Authorization") String token ){
        var username = jwtTokenUtil.getUsernameFromToken(token);
        var user = usuarioService.findOneByEmail(username);
        return (user.getRol().getId() == 1);
    }
}
