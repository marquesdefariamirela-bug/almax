package sp.senai.org.controlepatrimonio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sp.senai.org.controlepatrimonio.model.entity.Produto;
import sp.senai.org.controlepatrimonio.model.enums.StatusProduto;

import java.util.Optional;

public interface ProdutoRepository
        extends JpaRepository<Produto, Long> {
    long countByStatus(StatusProduto status);

    long countByTagIsNotNull();

    Optional<Produto> findByNumeroPatrimonio(String numeroPatrimonio);

    Optional<Produto> findByTag(String tagRfid);
}
