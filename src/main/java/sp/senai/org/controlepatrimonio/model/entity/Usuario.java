package sp.senai.org.controlepatrimonio.model.entity;

import jakarta.persistence.*;
import lombok.*;
import sp.senai.org.controlepatrimonio.model.enums.PerfilUsuario;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "usuarios",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_usuario_username",
                        columnNames = "username"
                ),
                @UniqueConstraint(
                        name = "uk_usuario_email",
                        columnNames = "email"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Builder.Default
    private PerfilUsuario perfil = PerfilUsuario.OPERADOR;

    @Column(nullable = false)
    @Builder.Default
    private Boolean ativo = true;

    @OneToMany(mappedBy = "usuario")
    @Builder.Default
    private List<Movimentacao> movimentacoes = new ArrayList<>();
}
