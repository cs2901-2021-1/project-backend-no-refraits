package controller;

import business.AuthenticationUserService;
import business.UsuarioService;
import config.JwtTokenUtil;
import data.entities.ApiResponse;
import data.entities.AuthToken;
import data.entities.Login;
import data.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationUserService authenticationUserService;

    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ApiResponse<AuthToken> register(@RequestBody Login loginUser) throws AuthenticationException {
        System.out.println(loginUser.getEmail());
        System.out.println(loginUser.getPassword());

        final var user = usuarioService.findOneByEmail(loginUser.getEmail());

        System.out.println(user.getEmail());
        System.out.println(user.getGoogleid());
        System.out.println();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword()));
        final String token = jwtTokenUtil.generateToken(user);
        System.out.println(token);
        return new ApiResponse<>(200, "success", new AuthToken(token, user.getEmail(), user.getDireccion()));
    }

    @RequestMapping(value = "/update-password", method = RequestMethod.GET)
    public String updatePasswordUser(){
        Usuario u = usuarioService.findOneByEmail("jbellido@uc.cl");
        authenticationUserService.updatePassword(u.getId());
        return "OK";
    }
}
