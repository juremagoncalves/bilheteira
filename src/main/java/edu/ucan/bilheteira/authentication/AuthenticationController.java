package edu.ucan.bilheteira.authentication;

import edu.ucan.bilheteira.revokedToken.RevokedToken;
import edu.ucan.bilheteira.revokedToken.RevokedTokenRepository;
import edu.ucan.bilheteira.user.User;
import edu.ucan.bilheteira.user.UserRegistrationRequest;
import edu.ucan.bilheteira.user.UserRepositoty;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager  authenticationManager;

    @Autowired
    private AuthenticationService  authenticationService;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepositoty userRepositoty;

    @Autowired
    private RevokedTokenRepository revokedTokenRepository;


    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(),data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        String email = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();
        UserDetails user = userRepositoty.findByEmail(email);
        var token = tokenService.generationToken((User) user);
        return ResponseEntity.ok().body(token);
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegistrationRequest user){
        try{
            User newUser = authenticationService.register(
                    user.getUser(),
                    user.getPerson(),
                    user.getRoleIds()

            );
            return ResponseEntity.ok(newUser);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
//   @PostMapping("/logout")
   // public ResponseEntity<?> logou(){
//       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //SecurityContext securityContext = SecurityContextHolder.getContext();
        //SecurityContextHolder.setContext(securityContext);
        //securityContext.setAuthentication(null);
//        SecurityContextHolder.getContext().setAuthentication(authentication);

        //System.err.println("contextooo> " + SecurityContextHolder.getContext());
//        SecurityContextHolder.setContext(null);
              // SecurityContextHolder.getContext();

       // return ResponseEntity.ok("Desconectado com sucesso");
    //}


    public ResponseEntity<?> logout(@RequestHeader ("Authorization") String authHeader){
        String token = authHeader.replace("Bearer ", "");
        RevokedToken revokedToken = new RevokedToken();
        revokedToken.setToken(token);
        revokedToken.setDataRevogado(Instant.now());
        revokedTokenRepository.save(revokedToken);
        return ResponseEntity.ok("Desconectado com sucesso");
    }

}
