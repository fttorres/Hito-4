package com.neonpulse.domain.valueobject;

import java.util.Objects;

public record CityId(String value) {
    public CityId {
        Objects.requireNonNull(value, "El ID de la ciudad no puede ser nulo");
        if (value.isBlank()) {
            throw new IllegalArgumentException("El ID de la ciudad no puede estar vacío");
        }
    }

    public static CityId of(String value) {
        return new CityId(value);
    }
}
