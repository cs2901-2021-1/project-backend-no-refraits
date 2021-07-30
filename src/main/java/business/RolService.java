package business;

import data.entities.Rol;
import data.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RolService {
    private final RolRepository repository;

    @Autowired
    RolService(RolRepository repository) {
        this.repository = repository;
    }


    public Rol findOneByName(String name){
        return repository.findRolByName(name);
    }
}
