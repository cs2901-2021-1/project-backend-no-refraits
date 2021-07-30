package business;

import data.entities.Usuario;
import data.entities.UsuarioDisplay;
import data.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UsuarioService {
    protected static final String[] roles = {"Administrador de sistema", "Administrador de dirección", "Director Académico", "Usuario de Dirección"};

    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository repository, BCryptPasswordEncoder bcryptEncoder) {
        this.repository = repository;
        this.bcryptEncoder = bcryptEncoder;
    }

    private String roleName(long role){
        return roles[(int) role - 1];
    }

    public List<UsuarioDisplay> getAllUserToDisplay(String gmail){
        List<UsuarioDisplay> items = new ArrayList<>();
        var itemsUsuario = findAll();
        for (Usuario item : itemsUsuario){
            if (!gmail.equals(item.getEmail())){
                var user = new UsuarioDisplay(item.getNombre(), item.getEmail(), item.getDireccion(), item.getId(), roleName(repository.getRolidbyID(item.getId())));
                items.add(user);
            }
        }
        return items;
    }

    public boolean isUserRegistered(Usuario user) {
        return !user.getNombre().equals("");
    }

    public void registerUser(Usuario user, String password, String nombre){
        user.setNombre(nombre);
        user.setGoogleid(bcryptEncoder.encode(password));
        repository.save(user);
    }

    public List<UsuarioDisplay> getUsersUnderDirection(String direccion, String gmail){
        List<UsuarioDisplay> items = new ArrayList<>();
        var itemsUsuario = findAll();
        for (Usuario item : itemsUsuario){
            if (item.getDireccion().equals(direccion) && !item.getEmail().equals(gmail)){
                var user = new UsuarioDisplay(item.getNombre(), item.getEmail(), item.getDireccion(), item.getId(), roleName(item.getRol().getId()));
                items.add(user);
            }
        }
        return items;
    }

    public String getPrettyNameRolebyId(long roleId){
        return roleName(roleId);
    }

    public List<Usuario> findAll(){
        return repository.findAll();
    }

    public boolean existsByEmail(String email){return repository.existsByEmail(email);}

    public Boolean isSysAdmin(Usuario usuario){
        return usuario.getRol().getId()==1;
    }

    public Usuario findOneByEmail(String email){
        return repository.findUsuarioByEmail(email);
    }

    public Usuario findUsuarioByEmailAndNombreNotNull(String email){
        return repository.findUsuarioByEmailAndNombreNotNull(email);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
