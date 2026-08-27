package com.neonpulse.domain.entity;

import com.neonpulse.domain.exception.InvalidQuantityException;
import com.neonpulse.domain.valueobject.Quantity;

/**
 * Validador de reglas de compra en el dominio.
 */
public class PurchaseValidator {

    public static final int MAX_QUANTITY_PER_PURCHASE = Quantity.MAX_QUANTITY_PER_PURCHASE;

    public void processQuantity(int quantity) {
        if (quantity <= 0) {
            throw new InvalidQuantityException("La cantidad debe ser mayor a 0: " + quantity);
        }
        if (quantity > MAX_QUANTITY_PER_PURCHASE) {
            throw new InvalidQuantityException(
                    "La cantidad solicitada (" + quantity + ") supera el máximo permitido por compra ("
                            + MAX_QUANTITY_PER_PURCHASE + ")");
        }
    }

    public void processQuantity(Quantity quantity) {
        processQuantity(quantity.value());
    }
}
