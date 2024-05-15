package edu.ucan.bilheteira.classeServico;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.ucan.bilheteira.transporte.Transporte;
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
@Table(name = "classe_servico")
public class ClasseServico {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "pk_classe_servico")
    private UUID pkClasseServico;

    @NotNull(message = "O campo [Designação] não deve estar em branco")
    private String designacao;

    @Column(columnDefinition = "boolean default true")
    private boolean estado;

    @Column(name = "data_cadastro", updatable = false)
    @CreationTimestamp
    private LocalDateTime dataCadastro;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "fkClasseServico")
    private List<Transporte> transportes = new ArrayList<>();
}
