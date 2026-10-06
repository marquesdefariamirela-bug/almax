package sp.senai.org.controlepatrimonio.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TagRequest(

        @NotBlank(message = "O EPC da tag é obrigatório")
        @Size(max = 100, message = "O EPC deve ter no máximo 100 caracteres")
        String epc,

        @Size(max = 100, message = "O código da tag deve ter no máximo 100 caracteres")
        String codigo,

        Boolean ativa,

        Long produtoId
) {
}
