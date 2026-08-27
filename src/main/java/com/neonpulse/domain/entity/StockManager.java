package com.neonpulse.domain.entity;

import com.neonpulse.domain.exception.OutOfStockException;
import com.neonpulse.domain.valueobject.Quantity;

/**
 * Validador de inventario en el dominio.
 */
public class StockManager {

    public void checkAvailability(int stock, int quantity) {
        if (stock < quantity) {
            throw new OutOfStockException(
                    "Stock insuficiente para completar la compra. Disponible: "
                            + stock + ", Solicitado: " + quantity);
        }
    }

    public void checkAvailability(int stock, Quantity quantity) {
        checkAvailability(stock, quantity.value());
    }
}
