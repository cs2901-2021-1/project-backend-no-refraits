package controller;


import business.AuthenticationService;
import business.UsuarioService;
import config.JwtTokenUtil;
import data.entities.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
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


    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UsuarioService usuarioService, AuthenticationService authenticationService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.usuarioService = usuarioService;
    }

    @PostMapping(value = "/generate-token")
    public Response<AuthData> register(@RequestBody Login loginUser) throws AuthenticationException {
        final var user = usuarioService.findUsuarioByEmailAndNombreNotNull(loginUser.getEmail());
        if (user == null) {
            return new Response<>(404, "No existe este usuario", null);
        }
        System.out.println("aqui1");
        if(!usuarioService.isUserRegistered(user)){
            usuarioService.registerUser(user, loginUser.getPassword());
        }
        System.out.println("aqui2");

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword()));
        final String token = jwtTokenUtil.generateToken(user);
        return new Response<>(200, "success",
                new AuthData(token, user.getEmail(), usuarioService.getPrettyNameRolebyId(user.getRol().getId())));
    }


}
