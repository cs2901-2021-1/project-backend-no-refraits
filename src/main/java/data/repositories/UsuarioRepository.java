package data.repositories;

import data.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findUsuarioByEmailAndGoogleid(String email, String googleid);

    @Query(value = "SELECT * from usuario u where u.email= ?1", nativeQuery = true)
    Usuario findUsuarioByEmail (String email);

    Usuario findUsuarioByEmailAndNombreNotNull(String email);

    Usuario findUsuarioByNombre(String nombre);

    @Query(value = "SELECT rol_id from usuario u where u.id= ?1", nativeQuery = true)
    long getRolidbyID(Long id);

}
