package edu.ucan.bilheteira.user;

import edu.ucan.bilheteira.person.PersonEntity;
import edu.ucan.bilheteira.role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "usuario")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "pk_usuario")
    private UUID pkUsuario;
    @Column(nullable = false, unique = true)
    @Email(message = "O campo(email) deve conter um email válido")
    private String email;
    @Column(nullable = false)
    @Length(min = 10, max = 100, message = "A senha deve conter entre 10 e 100 caracteres")
    private String password;


    @ManyToOne
    @JoinColumn(name = "fk_pessoa", nullable = false)
    private PersonEntity fkPessoa;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_tipo_usuario",
            joinColumns = @JoinColumn(name = "fk_usuario"),
            inverseJoinColumns = @JoinColumn(name = "fk_tipo_usuario"))
    private List<Role> roles;

    @Column(columnDefinition = "boolean default true")
    private boolean estado;
    @Column(name = "data_cadastro", updatable = false)
    @CreationTimestamp
    private LocalDateTime dataCadastro;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return this.roles;
    }
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities()
//    {
//        if(this.roles)
//    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email ;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
