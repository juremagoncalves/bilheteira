package edu.ucan.bilheteira.authentication;

import edu.ucan.bilheteira.exceptioes.CustomEntityNotFoundException;
import edu.ucan.bilheteira.person.PersonEntity;
import edu.ucan.bilheteira.person.PersonRepository;
import edu.ucan.bilheteira.role.Role;
import edu.ucan.bilheteira.role.RoleRepository;
import edu.ucan.bilheteira.user.User;
import edu.ucan.bilheteira.user.UserRepositoty;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepositoty userRepositoty;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private RoleRepository roleRepository;

    private static final UUID DEFAULT_ROLE_ID = UUID.fromString("d0f7378b-56ed-4903-9fad-be6db32d983d");

    @Transactional
    public User register(User user, PersonEntity newPerson, List<UUID> roleId){
        UserDetails existingUser = this.userRepositoty.findByEmail(user.getEmail());
        if(existingUser != null){
            throw new RuntimeException("Já existe um usuário com este email");
        }
        PersonEntity person = this.personRepository.save(newPerson);
        List<Role> roles;
        if(roleId == null || roleId.isEmpty()){
            Role defaultRole = roleRepository.findById(DEFAULT_ROLE_ID)
                    .orElseThrow(() -> new CustomEntityNotFoundException("Role Padrão não encontrada"));
            roles = Collections.singletonList(defaultRole);
        }
        else{
            roles = roleRepository.findAllById(roleId);
            if (roles.size() != roleId.size()){
                throw new CustomEntityNotFoundException("Um ou mais papéis fornecidos são inválidos");
            }
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encryptedPassword);
        user.setEstado(true);
        user.setRoles(roles);
        user.setFkPessoa(person);
       return  userRepositoty.save(user);
    }
}
