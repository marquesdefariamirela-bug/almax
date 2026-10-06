package sp.senai.org.controlepatrimonio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sp.senai.org.controlepatrimonio.model.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository
        extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
}
