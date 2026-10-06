package sp.senai.org.controlepatrimonio.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import sp.senai.org.controlepatrimonio.model.enums.TipoMovimentacao;

import java.time.LocalDate;

public record MovimentacaoRequest(

        @NotNull(message = "O produto é obrigatório")
        Long produtoId,

        Long ambienteOrigemId,

        Long ambienteDestinoId,

        Long leitorId,

        Long usuarioId,

        @NotNull(message = "O tipo de movimentação é obrigatório")
        TipoMovimentacao tipo,

        LocalDate dataHora,

        @Size(max = 500, message = "A observação deve ter no máximo 500 caracteres")
        String observacao
) {
}
