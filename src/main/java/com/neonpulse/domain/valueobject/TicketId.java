package com.neonpulse.domain.valueobject;

import java.util.Objects;

/**
 * Identificador único de ticket.
 */
public record TicketId(String value) {

    public TicketId {
        Objects.requireNonNull(value, "El ID del ticket no puede ser nulo");
        if (value.isBlank()) {
            throw new IllegalArgumentException("El ID del ticket no puede estar vacío");
        }
    }

    public static TicketId of(String value) {
        return new TicketId(value);
    }
}
