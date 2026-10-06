package sp.senai.org.controlepatrimonio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sp.senai.org.controlepatrimonio.model.entity.Ambiente;

public interface AmbienteRepository
        extends JpaRepository<Ambiente, Long> {
}
