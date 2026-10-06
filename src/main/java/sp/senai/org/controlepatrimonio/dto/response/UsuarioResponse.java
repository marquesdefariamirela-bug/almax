package sp.senai.org.controlepatrimonio.dto.response;

import sp.senai.org.controlepatrimonio.model.enums.PerfilUsuario;

public record UsuarioResponse(

        Long id,
        String nome,
        String email,
        Boolean ativo,
        PerfilUsuario perfil
) {
}
