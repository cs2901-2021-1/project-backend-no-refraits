package business;

import data.entities.Rol;
import data.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RolService {
    @Autowired
    private RolRepository repository;

    public List<Rol> findAll(){
        return repository.findAll();
    }

    public Rol findOneByName(String name){
        return repository.findRolByName(name);
    }


    public Rol create(Rol item) {
        return repository.save(item);
    }

    public Rol update(Rol newRol, Long id) {
        Optional<Rol> findRol = repository.findById(id);
        if (findRol.isPresent()) {
            var rol = findRol.get();
            rol.setName(newRol.getName());
            return repository.save(rol);
        } else {
            newRol.setId(id);
            return repository.save(newRol);
        }
    }

}
