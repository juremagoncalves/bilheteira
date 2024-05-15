package edu.ucan.bilheteira.configuration;

import edu.ucan.bilheteira.authentication.TokenService;
import edu.ucan.bilheteira.revokedToken.RevokedTokenService;
import edu.ucan.bilheteira.user.UserRepositoty;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    UserRepositoty userRepositoty;
    @Autowired
    RevokedTokenService revokedTokenService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var token = this.recoverToken(request);
        if (token != null){

            var email = this.tokenService.validationToken(token);
            UserDetails user = userRepositoty.findByEmail(email);
            if (user != null){
                user.getAuthorities().forEach(authority -> {
                });
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Usuário não autorizado");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
    public  String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
