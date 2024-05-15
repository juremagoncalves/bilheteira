package edu.ucan.bilheteira.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import edu.ucan.bilheteira.user.User;
import edu.ucan.bilheteira.user.UserRepositoty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class TokenService {

    @Autowired
    UserRepositoty userRepositoty;
    @Value("${api.security.token.secret}")
    private String secret;

    public JwtAuthenticationResponse generationToken(User user) {
        var expireIn = Instant.now().plus(Duration.ofHours(2));

        Algorithm algorithm = Algorithm.HMAC256(secret);

        var token = JWT.create()
                .withIssuer("bilheteira-api")
                .withSubject(user.getEmail())
                .withClaim("nome", user.getFkPessoa().getNome())
                .withClaim("pkUsuario", user.getPkUsuario().toString())
                .withClaim("roles", user.getRoles().stream()
                        .map(role -> role.getDesignacao().name())
                        .collect(Collectors.toList()))
                .withExpiresAt(expireIn)
                .sign(algorithm);

        return JwtAuthenticationResponse.builder().token(token).build();
    }
//    public String generationToken(User user){
//        var expireIn = Instant.now().plus(Duration.ofHours(2));
//        try {
//            Algorithm algorithm = Algorithm.HMAC256(secret);
//
//            return JWT.create()
//                    .withIssuer("bilheteira-api")
//                    .withSubject(user.getEmail())
//                    .withClaim("roles", user.getRoles().stream()
//                    .map(role -> role.getDesignacao().name())
//                    .collect(Collectors.toList()))
//                    .withExpiresAt(expireIn)
//                    .sign(algorithm);
//        }
//        catch (JWTCreationException exception){
//            throw new RuntimeException("Erro ao gerar o token", exception);
//        }
//    }

    public DecodedJWT validateToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .build()
                    .verify(token);

        }
        catch (JWTCreationException exception){
            return null;
        }
    }
    public String validationToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("bilheteira-api")
                    .build()
                    .verify(token)
                    .getSubject();



        }
        catch (JWTCreationException exception){
            return"";
        }
    }
}
