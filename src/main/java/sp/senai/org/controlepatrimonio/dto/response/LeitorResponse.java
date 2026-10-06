package sp.senai.org.controlepatrimonio.dto.response;

public record LeitorResponse(

        Long id,
        String nome,
        String identificador,
        String ip,
        String fabricante,
        String modelo,
        Boolean ativo,
        Long ambienteId,
        String ambienteNome,
        Integer quantidadeMovimentacoes
) {
}
