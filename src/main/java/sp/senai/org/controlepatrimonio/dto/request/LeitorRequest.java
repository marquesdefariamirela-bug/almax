package sp.senai.org.controlepatrimonio.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LeitorRequest(

        @NotBlank(message = "O nome do leitor é obrigatório")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        String nome,

        @NotBlank(message = "O identificador do leitor é obrigatório")
        @Size(max = 100, message = "O identificador deve ter no máximo 100 caracteres")
        String identificador,

        @Size(max = 50, message = "O IP deve ter no máximo 50 caracteres")
        String ip,

        @Size(max = 100, message = "O fabricante deve ter no máximo 100 caracteres")
        String fabricante,

        @Size(max = 100, message = "O modelo deve ter no máximo 100 caracteres")
        String modelo,

        Boolean ativo,

        Long ambienteId
) {
}
