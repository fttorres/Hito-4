package com.neonpulse.domain.exception;

/**
 * Excepción de dominio para stock insuficiente.
 */
public class OutOfStockException extends RuntimeException {
    public OutOfStockException(String message) {
        super(message);
    }
}
