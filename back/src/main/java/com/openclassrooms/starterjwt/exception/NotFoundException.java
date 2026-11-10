package com.openclassrooms.starterjwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception métier : ressource introuvable.
 * <p>
 * Mappée en HTTP 404 (via {@link ResponseStatus} et/ou {@link GlobalExceptionHandler}).
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    /** Constructeur sans message. */
    public NotFoundException() {
        super();
    }

    /**
     * @param message détail de l'erreur (ex. "Session not found with id: 12")
     */
    public NotFoundException(String message) {
        super(message);
    }
}
