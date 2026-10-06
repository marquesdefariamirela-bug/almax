package sp.senai.org.controlepatrimonio.model.entity;

import jakarta.persistence.*;
import lombok.*;
import sp.senai.org.controlepatrimonio.model.enums.StatusProduto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "produtos",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_produto_numero_patrimonio",
                        columnNames = "numero_patrimonio"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_patrimonio", nullable = false, length = 50)
    private String numeroPatrimonio;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @Column(length = 100)
    private String marca;

    @Column(length = 100)
    private String modelo;

    @Column(length = 100)
    private String numeroSerie;

    @Column(precision = 15, scale = 2)
    private BigDecimal valor;

    private LocalDate dataAquisicao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Builder.Default
    private StatusProduto status = StatusProduto.ATIVO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "ambiente_id",
            foreignKey = @ForeignKey(name = "fk_produto_ambiente")
    )
    private Ambiente ambiente;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "tag_id",
            unique = true,
            foreignKey = @ForeignKey(name = "fk_produto_tag")
    )
    private Tag tag;

    @OneToMany(mappedBy = "produto")
    @Builder.Default
    private List<Movimentacao> movimentacoes = new ArrayList<>();
}
