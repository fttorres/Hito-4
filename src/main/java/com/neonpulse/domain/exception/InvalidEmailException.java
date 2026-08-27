package com.neonpulse.domain.exception;

/**
 * Excepción de dominio para formatos de correo electrónico inválidos.
 */
public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String message) {
        super(message);
    }
}
