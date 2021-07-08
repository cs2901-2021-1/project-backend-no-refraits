package data.repositories;

import data.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findUsuarioByEmailAndGoogleid(String email, String googleid);
    Usuario findUsuarioByEmail(String email);
}
