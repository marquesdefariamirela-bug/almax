package sp.senai.org.controlepatrimonio.config;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sp.senai.org.controlepatrimonio.model.entity.Usuario;
import sp.senai.org.controlepatrimonio.model.enums.PerfilUsuario;
import sp.senai.org.controlepatrimonio.repository.UsuarioRepository;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.nome}")
    private String adminNome;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.senha}")
    private String adminSenha;

    @Override
    @NullMarked
    public void run(String... args) {

        if (usuarioRepository.findByEmail(adminEmail).isPresent()) {
            return;
        }

        Usuario admin = Usuario.builder()
                .username(adminEmail)
                .nome(adminNome)
                .email(adminEmail)
                .senha(passwordEncoder.encode(adminSenha))
                .ativo(true)
                .perfil(PerfilUsuario.ADMIN)
                .build();

        usuarioRepository.save(admin);

        System.out.println(
                "=============================================="
        );

        System.out.println(
                "Usuário administrador inicial criado."
        );

        System.out.println(
                "E-mail: " + adminEmail
        );

        System.out.println(
                "=============================================="
        );
    }
}