package business;


import config.JwtTokenUtil;
import data.entities.Rol;
import data.entities.Usuario;
import data.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private String roleName(long role){
        var retorno = "";
        if (role == 1){
            retorno = "Administrador de sistema";
        }else if(role == 2) {
            retorno = "Administrador de dirección";
        }
        else if(role == 3){
            retorno = "Director Académico";
        }else if(role == 4){
            retorno = "Usuario de Dirección";
        }
        return retorno;
    }

    public List<Usuario> getAllUserToDisplay(String gmail){
        List<Usuario> items = new ArrayList<>();
        var itemsUsuario = findAll();
        for (Usuario item : itemsUsuario){
            if (item.getEmail() != null){
                if(!item.getEmail().equals(gmail)){
                    var user = new Usuario(item.getNombre(), item.getEmail(), item.getDireccion(), item.getId());
                    items.add(user);
                }
            }
        }
        return items;
    }


    public String getPrettyNameRolebyId(long roleId){
        return roleName(roleId);
    }
    public List<Usuario> getAllUsertoDisplay(String direccion,String gmail){
        List<Usuario> items = new ArrayList<>();
        var itemsUsuario = findAll();
        for (Usuario item : itemsUsuario){
            if (item.getEmail() != null && item.getDireccion().equals(direccion)){
                if(!item.getEmail().equals(gmail)){
                    var user = new Usuario(item.getNombre(), item.getEmail(), item.getDireccion(), item.getId());
                    items.add(user);
                }
            }
        }
        return items;
    }

    public List<Usuario> findAll(){
        List<Usuario> items = new ArrayList<>();
        for (Usuario item :repository.findAll()) {
            var user = new Usuario();
            items.add(user);
            items.add(item);
        }
        return items;
    }

    public Boolean isSysAdmin(Usuario usuario){
        return usuario.getRol().getId()==1;
    }

    public  Boolean isDirAdmin(Usuario usuario){
        return usuario.getRol().getId()==2;
    }

    public Usuario findOne(long id){
        return repository.findById(id).get();
    }

    public Usuario create(Usuario item){
        return repository.save(item);
    }

    public Usuario update(Usuario newUsuario, Long id){
        Optional<Usuario> findUsuario = repository.findById(id);
        if (findUsuario.isPresent()) {
            var usuario = findUsuario.get();
            usuario.setNombre(newUsuario.getNombre() == null ? usuario.getNombre() : newUsuario.getNombre());
            usuario.setEmail(newUsuario.getEmail() == null ? usuario.getEmail() : newUsuario.getEmail());
            usuario.setGoogleid(newUsuario.getGoogleid() == null ? usuario.getGoogleid() : newUsuario.getGoogleid());
            usuario.setRol(newUsuario.getRol() == null ? usuario.getRol() : newUsuario.getRol());
            return usuario;
        } else {
            newUsuario.setId(id);
            return repository.save(newUsuario);
        }
    }

    public void delete(Long id){
        repository.delete(findOne(id));
    }

    public Usuario findOneByEmail(String email){
        return repository.findUsuarioByEmail(email);
    }

    public Usuario findUsuarioByEmailAndNombreNotNull(String email){
        return repository.findUsuarioByEmailAndNombreNotNull(email);
    }

    public Usuario findOneByNombre(String nombre){
        return repository.findUsuarioByNombre(nombre);
    }

    public Usuario save(Usuario newUsuario) {
        return repository.save(newUsuario);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
