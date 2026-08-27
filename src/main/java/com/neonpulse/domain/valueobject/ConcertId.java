package com.neonpulse.domain.valueobject;

import java.util.Objects;

/**
 * Identificador único de concerto.
 */
public record ConcertId(String value) {

    public ConcertId {
        Objects.requireNonNull(value, "El ID del concerto no puede ser nulo");
        if (value.isBlank()) {
            throw new IllegalArgumentException("El ID del concerto no puede estar vacío");
        }
    }

    public static ConcertId of(String value) {
        return new ConcertId(value);
    }
}
