package sp.senai.org.controlepatrimonio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sp.senai.org.controlepatrimonio.model.entity.Tag;

import java.util.Optional;

public interface TagRepository
        extends JpaRepository<Tag, Long> {

    Optional<Tag> findByEpc(String epc);
}
