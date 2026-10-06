package sp.senai.org.controlepatrimonio.dto.response;

import sp.senai.org.controlepatrimonio.model.enums.StatusProduto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProdutoResponse(

        Long id,
        String numeroPatrimonio,
        String nome,
        String descricao,
        String marca,
        String modelo,
        String numeroSerie,
        BigDecimal valor,
        LocalDate dataAquisicao,
        StatusProduto status,

        Long ambienteId,
        String ambienteNome,

        Long tagId,
        String tagEpc
) {
}
