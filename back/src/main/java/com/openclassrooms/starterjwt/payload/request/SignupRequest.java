package com.openclassrooms.starterjwt.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;



/**
 * Corps de la requête {@code POST /api/auth/register}.
 */
@Data
public class SignupRequest {
    /** Email unique du nouveau compte. */
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    /** Prénom (3–20 caractères). */
    @NotBlank
    @Size(min = 3, max = 20)
    private String firstName;

    /** Nom (3–20 caractères). */
    @NotBlank
    @Size(min = 3, max = 20)
    private String lastName;

    /** Mot de passe en clair (6–40), hashé avant persistence. */
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
