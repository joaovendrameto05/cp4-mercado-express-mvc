package fiap.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TDS_MVC_TB_MERCADO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;
    private String tipo;
    private String setor;
    private String tamanho;
    private Double preco;
}