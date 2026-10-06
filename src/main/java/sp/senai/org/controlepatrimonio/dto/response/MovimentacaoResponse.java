package sp.senai.org.controlepatrimonio.dto.response;

import sp.senai.org.controlepatrimonio.model.enums.TipoMovimentacao;

import java.time.LocalDateTime;

public record MovimentacaoResponse(

        Long id,
        Long produtoId,
        String numeroPatrimonio,
        String produtoNome,
        Long ambienteOrigemId,
        String ambienteOrigemNome,
        Long ambienteDestinoId,
        String ambienteDestinoNome,
        Long leitorId,
        String leitorNome,
        Long usuarioId,
        String usuarioNome,
        TipoMovimentacao tipo,
        LocalDateTime dataHora,
        String observacao
) {
}
