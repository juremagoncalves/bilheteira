package edu.ucan.bilheteira.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface UserRepositoty extends JpaRepository<User, UUID> {
    UserDetails findByEmail(String email);
}
