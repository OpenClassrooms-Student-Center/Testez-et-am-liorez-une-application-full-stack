package com.openclassrooms.starterjwt.security.jwt;

import org.springframework.stereotype.Component;

/**
 * Point d'entrée historique pour les erreurs d'authentification (401).
 * <p>
 * L'implémentation {@code AuthenticationEntryPoint} est commentée dans le starter.
 * En pratique, {@link com.openclassrooms.starterjwt.security.WebSecurityConfig}
 * utilise un lambda {@code sendError(401)} à la place.
 * <p>
 * Classe conservée pour compatibilité avec le code fourni / éventuelle réactivation.
 */
@Component
public class AuthEntryPointJwt { // implements AuthenticationEntryPoint
//
//    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);
//
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
//            throws IOException, ServletException {
//        logger.error("Unauthorized error: {}", authException.getMessage());
//
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//
//        final Map<String, Object> body = new HashMap<>();
//        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
//        body.put("error", "Unauthorized");
//        body.put("message", authException.getMessage());
//        body.put("path", request.getServletPath());
//
//        final ObjectMapper mapper = new ObjectMapper();
//        mapper.writeValue(response.getOutputStream(), body);
//    }

}
