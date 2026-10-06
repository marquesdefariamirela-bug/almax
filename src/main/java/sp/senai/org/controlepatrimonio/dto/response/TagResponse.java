package sp.senai.org.controlepatrimonio.dto.response;

public record TagResponse(

        Long id,
        String epc,
        String codigo,
        Boolean ativo,
        Long produtoId,
        String numeroPatrimonio,
        String produtoNome
) {
}
