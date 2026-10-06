package sp.senai.org.controlepatrimonio.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProdutoRequest(

        @NotBlank(message = "O número do patrimônio é obrigatório")
        @Size(max = 50)
        String numeroPatrimonio,

        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 150)
        String nome,

        @Size(max = 500)
        String descricao,

        @Size(max = 100)
        String marca,

        @Size(max = 100)
        String modelo,

        @Size(max = 100)
        String numeroSerie,

        @PositiveOrZero(message = "O valor não pode ser negativo")
        BigDecimal valor,

        LocalDate dataAquisicao,

        Long ambienteId,

        Long tagId
) {
}
