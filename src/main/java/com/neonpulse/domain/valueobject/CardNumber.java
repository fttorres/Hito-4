package com.neonpulse.domain.valueobject;

/**
 * Value Object inmutable para representar un medio de pago / número de tarjeta.
 */
public record CardNumber(String value) {

    public CardNumber {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de tarjeta no puede estar vacío");
        }
        value = value.trim();
    }

    public static CardNumber of(String value) {
        return new CardNumber(value);
    }

    public String getLast4Digits() {
        return value.length() >= 4 ? value.substring(value.length() - 4) : value;
    }
}
