package edu.ucan.bilheteira.revokedToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RevokedTokenService {

    @Autowired
    private RevokedTokenRepository revokedTokenRepository;

    public boolean isTokenRevoked(String token){
        return this.revokedTokenRepository.existsByToken(token);
    }
}
