package com.neonpulse.domain.exception;

/**
 * Excepción de dominio cuando la cantidad ingresada no cumple las reglas de negocio.
 */
public class InvalidQuantityException extends RuntimeException {
    public InvalidQuantityException(String message) {
        super(message);
    }
}
