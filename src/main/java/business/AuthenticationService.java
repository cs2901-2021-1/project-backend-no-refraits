package business;

import data.entities.Usuario;
import data.repositories.UsuarioRepository;
import org.springframework.beans.BeanUtils;
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
    private final UsuarioRepository userDao;

    private final BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    public AuthenticationService(UsuarioRepository userDao, BCryptPasswordEncoder bcryptEncoder) {
        this.userDao = userDao;
        this.bcryptEncoder = bcryptEncoder;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = userDao.findUsuarioByEmail(username);
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

    /**
     private List<SimpleGrantedAuthority> getAuthority() {
     return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
     }**/

    public List<Usuario> findAll() {
        List<Usuario> list = new ArrayList<>();
        userDao.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    public void delete(long id) {
        userDao.deleteById(id);
    }

    public Usuario findOne(String username) {
        return userDao.findUsuarioByEmail(username);
    }

    public Usuario findById(long id) {
        Optional<Usuario> optionalUser = userDao.findById(id);
        return optionalUser.isPresent() ? optionalUser.get() : null;
    }


    public Usuario updatePassword(Long codigo) {
        Usuario user = findById(codigo);
        if(user != null) {
            user.setGoogleid(bcryptEncoder.encode(user.getGoogleid()));
            userDao.save(user);
        }
        return user;
    }

    public Usuario update(Usuario userDto) {
        Usuario user = findById(userDto.getId());
        if(user != null) {
            BeanUtils.copyProperties(userDto, user, "password");
            userDao.save(user);
        }
        return userDto;
    }

    public Usuario save(Usuario user) {
        user.setGoogleid(bcryptEncoder.encode(user.getGoogleid()));
        return userDao.save(user);
    }
}