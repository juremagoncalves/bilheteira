package edu.ucan.bilheteira.tipoTransporte;

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
@Table(name = "tipo_transporte")
public class TipoTransporte {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "pk_tipo_transporte")
    private UUID pkTipoTransporte;

    @NotNull(message = "O campo [designacao] não pode estar em branco")
    private String designacao;

    @Column(columnDefinition = "boolean default true")
    private boolean estado;

    @Column(name = "data_cadastro")
    @CreationTimestamp
    private LocalDateTime dataCadastro;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "fkTipoTransporte")
    private List<Transporte> transportes = new ArrayList<>();

}
