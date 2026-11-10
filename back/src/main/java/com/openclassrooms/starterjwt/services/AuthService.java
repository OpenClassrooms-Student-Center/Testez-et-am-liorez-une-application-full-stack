package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import com.openclassrooms.starterjwt.payload.response.MessageResponse;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service d'authentification.
 * <p>
 * Centralise la logique métier de login et d'inscription :
 * authentification Spring Security, génération JWT, hash du mot de passe.
 * Les controllers se limitent à exposer les endpoints HTTP.
 */
@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    /**
     * @param authenticationManager vérifie email/mot de passe
     * @param jwtUtils              génération du token JWT
     * @param passwordEncoder       hash BCrypt des mots de passe
     * @param userService           service métier utilisateurs
     */
    public AuthService(AuthenticationManager authenticationManager,
                       JwtUtils jwtUtils,
                       PasswordEncoder passwordEncoder,
                       UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    /**
     * Authentifie un utilisateur et construit la réponse JWT.
     * <p>
     * Le flag admin est lu depuis {@link UserDetailsImpl} (rempli au chargement user).
     *
     * @param loginRequest email et mot de passe
     * @return {@link JwtResponse} (token + infos utilisateur)
     */
    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getFirstName(),
                userDetails.getLastName(),
                userDetails.getAdmin()
        );
    }

    /**
     * Inscrit un nouvel utilisateur (toujours non-admin).
     *
     * @param signUpRequest données d'inscription
     * @return message de succès
     */
    public MessageResponse register(SignupRequest signUpRequest) {
        this.userService.register(
                signUpRequest.getEmail(),
                signUpRequest.getLastName(),
                signUpRequest.getFirstName(),
                passwordEncoder.encode(signUpRequest.getPassword())
        );

        return new MessageResponse("User registered successfully!");
    }
}
