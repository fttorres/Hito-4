package com.neonpulse.domain.exception;

/**
 * Excepción lanzada cuando un recurso solicitado no es encontrado.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
