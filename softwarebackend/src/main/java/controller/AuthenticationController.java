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
        final var user = usuarioService.findOneByEmail(loginUser.getEmail());
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
        System.out.println(token);
        System.out.println(user.getRol().getName());

        var test = jwtTokenUtil.getUsernameFromToken(token);
        System.out.println(test);
        System.out.println(usuarioService.findOneByEmail(test));
        return new ApiResponse<>(200, "success",
                new AuthData(token, user.getEmail(), user.getDireccion()));
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


}
