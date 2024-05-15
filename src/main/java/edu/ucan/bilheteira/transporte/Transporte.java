package edu.ucan.bilheteira.transporte;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.ucan.bilheteira.assento.Assento;
import edu.ucan.bilheteira.classeServico.ClasseServico;
import edu.ucan.bilheteira.marca.Marca;
import edu.ucan.bilheteira.programacao.Programacao;
import edu.ucan.bilheteira.tipoTransporte.TipoTransporte;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "transporte")
public class Transporte {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "pk_transporte")
    private UUID pkTransporte;

    @NotNull(message = "O campo [matricula] não deve estar em branco")
    @Column(nullable = false, unique = true)
    private String matricula;

    @NotNull(message = "O campo [capacidade] não deve estar em branco")
    @Column(nullable = false)
    private Integer capacidade;

    @ManyToOne
    @JoinColumn(nullable = false, name = "fk_marca")
    @NotNull(message = "O campo [Marca] não deve estar em branco")
    private Marca fkMarca;

    @ManyToOne
    @JoinColumn(nullable = false, name = "fk_tipo_transporte")
    @NotNull(message = "O campo [Tipo Transporte] não deve estar em branco")
    private TipoTransporte fkTipoTransporte;

    @ManyToOne
    @JoinColumn(nullable = false, name = "fk_classe_servico")
    @NotNull(message = "O campo [Classe serviço] não deve estar em branco")
    private ClasseServico fkClasseServico;

    @Column(columnDefinition = "boolean default true")
    private boolean estado;

    @Column(name = "data_cadastro", updatable = false)
    @CreationTimestamp
    private LocalDateTime dataCadastro;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "fkTransporte")
    private List<Programacao> programacaos = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "fkTransporte")
    private List<Assento> assentos = new ArrayList<>();
}
