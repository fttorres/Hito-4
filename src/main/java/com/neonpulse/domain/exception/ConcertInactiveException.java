package com.neonpulse.domain.exception;

/**
 * Excepción lanzada cuando se intenta interactuar con un concierto o concerto inactivo o cancelado.
 */
public class ConcertInactiveException extends RuntimeException {
    public ConcertInactiveException(String message) {
        super(message);
    }
}
