package edu.ucan.bilheteira.role;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "tipo_usuario")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "pk_tipo_usuario")
    private UUID pkTipoUsuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleName designacao;

    @Column(columnDefinition = "boolean default true")
    private boolean estado;

    @Column(name = "data_cadastro", updatable = false)
    @CreationTimestamp
    private LocalDateTime dataCadastro;

    @Override
    public String getAuthority() {
        return designacao.toString();
    }
}
