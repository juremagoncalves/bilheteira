package edu.ucan.bilheteira.localidade.provincia;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.ucan.bilheteira.rota.Rota;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "provincia")
public class Provincia {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "pk_provincia")
    private UUID pkProvincia;

    @Column(nullable = false)
    private String designacao ;

    @Column(columnDefinition = "boolean default true")
    private boolean estado;

    @Column(name = "data_cadastro", updatable = false)
    @CreationTimestamp
    private LocalDateTime dataCadastro;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "fkProvinciaOrigem")
    private List<Rota> rotasOrigem = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "fkProvinciaDestino")
    private List<Rota> rotasDestino = new ArrayList<>();
}
