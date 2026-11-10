package com.openclassrooms.starterjwt.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Réponse API simple contenant uniquement un message texte
 * (ex. succès d'inscription, email déjà pris).
 */
@Data
@AllArgsConstructor
public class MessageResponse {
    /** Message destiné au client. */
    private String message;
}
