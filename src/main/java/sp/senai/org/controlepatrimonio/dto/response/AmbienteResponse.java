package sp.senai.org.controlepatrimonio.dto.response;

public record AmbienteResponse(

        Long id,
        String nome,
        String descricao,
        Boolean ativo,
        Integer quantidadeProdutos,
        Integer quantidadeLeitores
) {
}
