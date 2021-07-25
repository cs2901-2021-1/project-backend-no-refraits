package business;

import data.entities.Usuario;
import data.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service(value = "AuthenticationService")
public class AuthenticationService implements UserDetailsService{
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    public AuthenticationService(UsuarioRepository usuarioRepository, BCryptPasswordEncoder bcryptEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.bcryptEncoder = bcryptEncoder;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = usuarioRepository.findUsuarioByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getGoogleid(), getAuthority(user));
    }
    private Set<SimpleGrantedAuthority> getAuthority(Usuario user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRol().getName()));
        return authorities;
    }


    public Usuario save(Usuario user) {
        user.setGoogleid(bcryptEncoder.encode(user.getGoogleid()));
        return usuarioRepository.save(user);
    }
}