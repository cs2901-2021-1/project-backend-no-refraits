package data.repositories;

import data.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Rol findRolByName(String name);
}