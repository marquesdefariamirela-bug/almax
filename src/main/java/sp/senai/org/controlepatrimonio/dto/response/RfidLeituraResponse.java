package sp.senai.org.controlepatrimonio.dto.response;

public record RfidLeituraResponse(
        boolean sucesso,
        String mensagem,
        Long produtoId,
        String produto,
        String ambiente
) {
}
