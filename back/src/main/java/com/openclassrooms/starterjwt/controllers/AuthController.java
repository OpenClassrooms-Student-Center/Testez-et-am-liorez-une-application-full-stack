package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import com.openclassrooms.starterjwt.payload.response.MessageResponse;
import com.openclassrooms.starterjwt.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur d'authentification (endpoints publics).
 * <p>
 * Expose login et register sous {@code /api/auth}.
 * Aucune logique métier ici : tout est délégué à {@link AuthService}.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    /**
     * @param authService service d'authentification
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Connecte un utilisateur et renvoie un JWT + infos de session.
     *
     * @param loginRequest email et mot de passe
     * @return {@link JwtResponse} (token, id, nom, admin, etc.)
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(this.authService.login(loginRequest));
    }

    /**
     * Inscrit un nouvel utilisateur (toujours non-admin).
     *
     * @param signUpRequest données d'inscription
     * @return message de succès
     */
    @PostMapping("/register")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return ResponseEntity.ok(this.authService.register(signUpRequest));
    }
}
