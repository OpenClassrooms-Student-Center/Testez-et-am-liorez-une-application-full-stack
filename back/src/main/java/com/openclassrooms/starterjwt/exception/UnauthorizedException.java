package com.openclassrooms.starterjwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception métier : action non autorisée pour l'utilisateur connecté.
 * <p>
 * Mappée en HTTP 401.
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

    /** Constructeur sans message. */
    public UnauthorizedException() {
        super();
    }

    /**
     * @param message détail de l'interdiction
     */
    public UnauthorizedException(String message) {
        super(message);
    }
}
