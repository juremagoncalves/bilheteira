package edu.ucan.bilheteira.authorization;

import edu.ucan.bilheteira.user.UserRepositoty;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class AuthorizationService implements UserDetailsService {
    @Autowired
    UserRepositoty userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findByEmail(username);
        UserDetails user = userRepository.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + username);

        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }
}
