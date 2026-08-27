package com.neonpulse.domain.exception;

/**
 * Excepción de dominio cuando el concerto no existe en la persistencia.
 */
public class ConcertNotFoundException extends RuntimeException {
    public ConcertNotFoundException(String message) {
        super(message);
    }
}
