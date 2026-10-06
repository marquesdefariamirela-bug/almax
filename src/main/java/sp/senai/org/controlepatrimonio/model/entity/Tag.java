package sp.senai.org.controlepatrimonio.model.entity;

import jakarta.persistence.*;
import lombok.*;
import sp.senai.org.controlepatrimonio.model.enums.StatusTag;

@Entity
@Table(
        name = "tags",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_tag_epc",
                        columnNames = "epc"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String epc;

    @Column(length = 100)
    private String codigo;

    @Builder.Default
    @Column(nullable = false)
    private Boolean ativa = true;

    @OneToOne
    @JoinColumn(name = "produto_id", unique = true)
    private Produto produto;
}
