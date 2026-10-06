package sp.senai.org.controlepatrimonio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sp.senai.org.controlepatrimonio.model.entity.Leitor;

import java.util.Optional;

public interface LeitorRepository
        extends JpaRepository<Leitor, Long> {

    Optional<Leitor> findByIdentificador(String identificador);
}
