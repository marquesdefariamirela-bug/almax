package sp.senai.org.controlepatrimonio.dto.response;

import java.time.LocalDateTime;

public record MovimentacaoDashboardResponse(
        Long id,
        String produto,
        String ambienteOrigem,
        String ambienteDestino,
        LocalDateTime dataMovimentacao
) {
}
