package com.openclassrooms.starterjwt.security.jwt;

import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Utilitaire JWT : génération, lecture et validation des tokens.
 * <p>
 * Le secret et la durée de vie viennent de {@code application.yml}
 * ({@code oc.app.jwtSecret} / {@code oc.app.jwtExpirationMs}).
 */
@Slf4j
@Component
public class JwtUtils {
    @Value("${oc.app.jwtSecret}")
    private String jwtSecret;

    @Value("${oc.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * Génère un JWT signé pour l'utilisateur authentifié.
     * <p>
     * Le subject du token est l'email (username).
     *
     * @param authentication résultat de l'authentification Spring Security
     * @return token JWT compact (string)
     */
    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Extrait l'email (subject) contenu dans le token.
     *
     * @param token JWT
     * @return email / username
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).build().parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Vérifie que le token est bien formé, signé et non expiré.
     *
     * @param authToken JWT à valider
     * @return {@code true} si valide, sinon {@code false}
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).build().parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
