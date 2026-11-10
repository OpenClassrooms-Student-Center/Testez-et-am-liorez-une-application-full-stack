package com.openclassrooms.starterjwt.security.jwt;

import com.openclassrooms.starterjwt.security.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

/**
 * Filtre HTTP exécuté à chaque requête.
 * <p>
 * Lit le header {@code Authorization: Bearer &lt;token&gt;}, valide le JWT,
 * charge l'utilisateur et le place dans le {@link SecurityContextHolder}.
 * Sans ce filtre, Spring Security ne saurait pas qui est connecté.
 */
public class AuthTokenFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    /** Utilitaire de lecture/validation JWT. */
    @Autowired
    private JwtUtils jwtUtils;

    /** Charge le UserDetails à partir de l'email du token. */
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * Intercepte la requête, authentifie si un JWT valide est présent, puis continue la chaîne.
     *
     * @param request     requête HTTP entrante
     * @param response    réponse HTTP
     * @param filterChain chaîne de filtres suivante
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extrait le token brut depuis le header Authorization.
     *
     * @param request requête HTTP
     * @return le JWT sans le préfixe {@code Bearer }, ou {@code null}
     */
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }
}
