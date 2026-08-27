package com.neonpulse.domain.valueobject;

import com.neonpulse.domain.exception.InvalidQuantityException;

/**
 * Value Object (record) inmutable y auto-validante para la cantidad de entradas.
 */
public record Quantity(int value) {

    public static final int MAX_QUANTITY_PER_PURCHASE = 10;

    public Quantity {
        if (value <= 0) {
            throw new InvalidQuantityException("La cantidad debe ser mayor a 0: " + value);
        }
        if (value > MAX_QUANTITY_PER_PURCHASE) {
            throw new InvalidQuantityException(
                    "La cantidad solicitada (" + value + ") supera el máximo permitido por compra ("
                            + MAX_QUANTITY_PER_PURCHASE + ")");
        }
    }

    public static Quantity of(int value) {
        return new Quantity(value);
    }
}
