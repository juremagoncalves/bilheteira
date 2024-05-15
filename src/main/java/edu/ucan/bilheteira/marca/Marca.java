package edu.ucan.bilheteira.marca;

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
@Table(name = "marca")
public class Marca {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID pkMarca;
    @NotNull(message = "O campo [designação] não deve estar em branco")
    @Column(nullable = false)
    private String designacao;
    @Column(columnDefinition = "boolean default true")
    private boolean estado;
    @Column(name = "data_cadastro")
    @CreationTimestamp
    private LocalDateTime dataCadastro;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "fkMarca")
    private List<Transporte> transportes = new ArrayList<>();

}
