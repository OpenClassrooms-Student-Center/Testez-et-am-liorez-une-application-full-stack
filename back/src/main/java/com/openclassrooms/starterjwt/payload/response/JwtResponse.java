package com.openclassrooms.starterjwt.payload.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Réponse de login : token JWT + infos utiles au front.
 */
@Getter
@Setter
public class JwtResponse {
    /** Token d'accès à mettre dans {@code Authorization: Bearer ...}. */
    private String token;
    /** Type du token (toujours {@code Bearer}). */
    private String type = "Bearer";
    /** Id utilisateur. */
    private Long id;
    /** Email / username. */
    private String username;
    private String firstName;
    private String lastName;
    /** Indique si le compte est admin. */
    private Boolean admin;

    /**
     * @param accessToken JWT généré
     * @param id          id user
     * @param username    email
     * @param firstName   prénom
     * @param lastName    nom
     * @param admin       droit admin
     */
    public JwtResponse(String accessToken, Long id, String username, String firstName, String lastName, Boolean admin) {
        this.token = accessToken;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.admin = admin;
    }
}
