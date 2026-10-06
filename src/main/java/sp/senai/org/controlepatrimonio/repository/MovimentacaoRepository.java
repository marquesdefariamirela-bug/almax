package sp.senai.org.controlepatrimonio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sp.senai.org.controlepatrimonio.model.entity.Movimentacao;

import java.util.List;

public interface MovimentacaoRepository
        extends JpaRepository<Movimentacao, Long> {

    List<Movimentacao> findTop10ByOrderByDataHoraDesc();

}