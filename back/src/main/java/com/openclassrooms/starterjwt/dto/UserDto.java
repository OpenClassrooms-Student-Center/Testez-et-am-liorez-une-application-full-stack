package com.openclassrooms.starterjwt.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

/**
 * DTO utilisateur renvoyé par l'API.
 * <p>
 * Le mot de passe est annoté {@link JsonIgnore} pour ne jamais partir dans le JSON.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @NonNull
    @Size(max = 50)
    @Email
    private String email;

    @NonNull
    @Size(max = 20)
    private String lastName;

    @NonNull
    @Size(max = 20)
    private String firstName;

    /** {@code true} si compte administrateur. */
    @NonNull
    private boolean admin;

    /** Jamais sérialisé dans les réponses JSON. */
    @JsonIgnore
    @Size(max = 120)
    private String password;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
