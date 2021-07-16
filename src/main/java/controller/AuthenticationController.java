package controller;

import business.AuthenticationUserService;
import business.RolService;
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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RolService rolService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UsuarioService usuarioService;


    @Autowired
    private AuthenticationUserService authenticationUserService;

    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ApiResponse<AuthData> register(@RequestBody Login loginUser) throws AuthenticationException {
        System.out.println(loginUser.getEmail());
        var user = usuarioService.findOneByEmail(loginUser.getEmail());
        if (user == null)
        {
            return new ApiResponse<>(404, "No existe este usuario", null);
        }
        if(user.getNombre().equals("")){
            var newUser = new Usuario(loginUser.getEmail(), loginUser.getNombre(),loginUser.getPassword(), user.getDireccion(),user.getRol());
            usuarioService.update(newUser, user.getId());
            authenticationUserService.updatePassword(user.getId());
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword()));
        final String token = jwtTokenUtil.generateToken(user);
        var test = jwtTokenUtil.getUsernameFromToken(token);
        return new ApiResponse<>(200, "success",
                new AuthData(token, user.getEmail(), usuarioService.getPrettyNameRolebyId(user.getRol().getId())));
    }

    /*
    @Autowired
    private UsuarioService service;

*/
    @RequestMapping(value = "/update-password", method = RequestMethod.GET)
    public String updatePasswordUser(){
        Usuario u = usuarioService.findOneByEmail("jbellido@uc.cl");
        authenticationUserService.updatePassword(u.getId());
        return "OK";
    }
    @RequestMapping(value="/checkiflogged", method = RequestMethod.GET)
    public boolean checkiflogged(@RequestHeader("Authorization") String token){
        var username = jwtTokenUtil.getUsernameFromToken(token);
        if (username == null){
            return false;
        }
        System.out.println(username);
        System.out.println(!Boolean.TRUE.equals(jwtTokenUtil.isTokenExpired(token)));
        return !Boolean.TRUE.equals(jwtTokenUtil.isTokenExpired(token));
    }
    @RequestMapping(value="/validatetypefofuser/{option}", method = RequestMethod.GET)
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


    @RequestMapping(value="/checkifuser", method = RequestMethod.GET)
    public Boolean checkifuser(@RequestHeader("Authorization") String token){
        var username = jwtTokenUtil.getUsernameFromToken(token);
        return username != null;
    }

    @RequestMapping(value="/checkifdiruser", method = RequestMethod.GET)
    public Boolean checkifdiruser(@RequestHeader("Authorization") String token ){
        var username = jwtTokenUtil.getUsernameFromToken(token);
        var user = usuarioService.findOneByEmail(username);
        return (user.getRol().getId() == 4);
    }

    @RequestMapping(value="/checkifdgauser", method=RequestMethod.GET)
    public Boolean checkifdgauser(@RequestHeader("Authorization") String token){
        var username = jwtTokenUtil.getUsernameFromToken(token);
        var user = usuarioService.findOneByEmail(username);
        return (user.getRol().getId() == 3);
    }

    @RequestMapping(value="/checkifadminuser", method = RequestMethod.GET)
    public Boolean checkifadminuser(@RequestHeader("Authorization") String token ){
        var username = jwtTokenUtil.getUsernameFromToken(token);
        var user = usuarioService.findOneByEmail(username);
        return (user.getRol().getId() == 2 || user.getRol().getId() == 1);
    }

    @RequestMapping(value="/checkifadmindiruser", method = RequestMethod.GET)
    public Boolean checkifadmindiruser(@RequestHeader("Authorization") String token ){
        var username = jwtTokenUtil.getUsernameFromToken(token);
        var user = usuarioService.findOneByEmail(username);
        return (user.getRol().getId() == 2);
    }

    @RequestMapping(value="/checkifsysadminuser", method = RequestMethod.GET)
    public Boolean checkifsysadminuser(@RequestHeader("Authorization") String token ){
        var username = jwtTokenUtil.getUsernameFromToken(token);
        var user = usuarioService.findOneByEmail(username);
        return (user.getRol().getId() == 1);
    }

}
