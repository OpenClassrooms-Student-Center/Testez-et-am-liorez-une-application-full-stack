package com.openclassrooms.starterjwt.payload.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Corps de la requête {@code POST /api/auth/login}.
 */
@Data
public class LoginRequest {
    /** Email du compte. */
    @NotBlank
    private String email;

    /** Mot de passe en clair (hashé côté serveur à la vérification). */
    @NotBlank
    private String password;

}
