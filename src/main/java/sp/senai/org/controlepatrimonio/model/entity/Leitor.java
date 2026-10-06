package sp.senai.org.controlepatrimonio.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "leitores",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_leitor_identificador",
                        columnNames = "identificador"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Leitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 100)
    private String identificador;

    @Column(length = 50)
    private String ip;

    @Column(length = 100)
    private String fabricante;

    @Column(length = 100)
    private String modelo;

    @Column(nullable = false)
    @Builder.Default
    private Boolean ativo = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "ambiente_id",
            foreignKey = @ForeignKey(name = "fk_leitor_ambiente")
    )
    private Ambiente ambiente;

    @OneToMany(mappedBy = "leitor")
    @Builder.Default
    private List<Movimentacao> movimentacoes = new ArrayList<>();
}
