package sp.senai.org.controlepatrimonio.model.entity;

import jakarta.persistence.*;
import lombok.*;
import sp.senai.org.controlepatrimonio.model.enums.TipoMovimentacao;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "produto_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_movimentacao_produto")
    )
    private Produto produto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "ambiente_origem_id",
            foreignKey = @ForeignKey(name = "fk_movimentacao_ambiente_origem")
    )
    private Ambiente ambienteOrigem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "ambiente_destino_id",
            foreignKey = @ForeignKey(name = "fk_movimentacao_ambiente_destino")
    )
    private Ambiente ambienteDestino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "leitor_id",
            foreignKey = @ForeignKey(name = "fk_movimentacao_leitor")
    )
    private Leitor leitor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "usuario_id",
            foreignKey = @ForeignKey(name = "fk_movimentacao_usuario")
    )
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TipoMovimentacao tipo;

    @Column(name = "data_hora", nullable = false)
    @Builder.Default
    private LocalDateTime dataHora = LocalDateTime.now();

    @Column(length = 500)
    private String observacao;
}
