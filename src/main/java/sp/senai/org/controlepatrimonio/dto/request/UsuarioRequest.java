package sp.senai.org.controlepatrimonio.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import sp.senai.org.controlepatrimonio.model.enums.PerfilUsuario;

public record UsuarioRequest(

        @NotBlank(message = "O nome é obrigatório")
        @Size(
                max = 150,
                message = "O nome deve ter no máximo 150 caracteres"
        )
        String nome,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Informe um e-mail válido")
        @Size(
                max = 150,
                message = "O e-mail deve ter no máximo 150 caracteres"
        )
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Size(
                min = 6,
                max = 100,
                message = "A senha deve ter entre 6 e 100 caracteres"
        )
        String senha,

        Boolean ativo,

        PerfilUsuario perfil
) {
}
