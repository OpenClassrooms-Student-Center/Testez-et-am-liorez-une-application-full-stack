package com.openclassrooms.starterjwt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * DTO d'une session de yoga (contrat API).
 * <p>
 * Différence importante avec l'entité : {@code teacher_id} est un Long,
 * alors que l'entité {@code Session} référence un objet {@code Teacher}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionDto {
    /** Identifiant de la session (null à la création). */
    private Long id;

    /** Nom de la session. */
    @NotBlank
    @Size(max = 50)
    private String name;

    /** Date de la séance. */
    @NotNull
    private Date date;

    /** Id du professeur associé. */
    @NotNull
    private Long teacher_id;

    /** Description textuelle. */
    @NotNull
    @Size(max = 2500)
    private String description;

    /** Ids des utilisateurs inscrits (participate). */
    private List<Long> users;

    /** Date de création. */
    private LocalDateTime createdAt;

    /** Date de dernière mise à jour. */
    private LocalDateTime updatedAt;
}
