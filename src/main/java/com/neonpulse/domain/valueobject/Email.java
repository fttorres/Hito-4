package com.neonpulse.domain.valueobject;

import com.neonpulse.domain.exception.InvalidEmailException;

/**
 * Value Object (record) inmutable y auto-validante para Email.
 */
public record Email(String value) {

    public Email {
        if (value == null || value.isBlank() || !value.contains("@")) {
            throw new InvalidEmailException("Formato de correo electrónico inválido.");
        }
        value = value.trim().toLowerCase();
    }
}
