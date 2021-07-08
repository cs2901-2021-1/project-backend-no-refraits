package business;

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

    public List<Usuario> findAll(){
        List<Usuario> items = new ArrayList<>();

        for (Usuario item :repository.findAll()) {
            items.add(item);
        }
        return items;
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

    public Usuario buscarUsuario(String email, String googleid){
        return repository.findUsuarioByEmailAndGoogleid(email, googleid);
    }

    public Usuario findOneByEmail(String email){
        return repository.findUsuarioByEmail(email);
    }

    public Usuario save(Usuario newUsuario) {
        return repository.save(newUsuario);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
