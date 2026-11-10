package com.openclassrooms.starterjwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception métier : requête invalide.
 * <p>
 * Mappée en HTTP 400. Utilisée par exemple pour un email déjà pris
 * ou une participation déjà existante.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    /** Constructeur sans message. */
    public BadRequestException() {
        super();
    }

    /**
     * @param message message renvoyé éventuellement au client
     */
    public BadRequestException(String message) {
        super(message);
    }
}
