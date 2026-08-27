package com.neonpulse.domain.entity;

import com.neonpulse.domain.exception.OutOfStockException;
import com.neonpulse.domain.valueobject.ConcertId;
import com.neonpulse.domain.valueobject.Money;
import com.neonpulse.domain.valueobject.Quantity;

import java.util.Objects;

/**
 * Entidad del dominio que representa un Concerto con identidad única (ConcertId) y ciclo de vida.
 */
public class Concert {

    private final ConcertId id;
    private final String band;
    private final String date;
    private final String status;
    private final Money unitPrice;
    private int availableStock;

    public Concert(ConcertId id, String band, String date, String status, Money unitPrice, int availableStock) {
        this.id = Objects.requireNonNull(id, "El ID del concerto no puede ser nulo");
        this.band = Objects.requireNonNull(band, "La banda del concerto no puede ser nula");
        this.date = date;
        this.status = status;
        this.unitPrice = Objects.requireNonNull(unitPrice, "El precio unitario no puede ser nulo");
        if (availableStock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        this.availableStock = availableStock;
    }

    public ConcertId getId() {
        return id;
    }

    public String getBand() {
        return band;
    }
    
    public String getDate() {
        return date;
    }
    
    public String getStatus() {
        return status;
    }

    public Money getUnitPrice() {
        return unitPrice;
    }

    public int getAvailableStock() {
        return availableStock;
    }

    public boolean hasEnoughStock(Quantity quantity) {
        Objects.requireNonNull(quantity, "La cantidad no puede ser nula");
        return availableStock >= quantity.value();
    }

    public void decreaseStock(Quantity quantity) {
        if (!hasEnoughStock(quantity)) {
            throw new OutOfStockException(
                    "Stock insuficiente para completar la compra. Disponible: "
                            + availableStock + ", Solicitado: " + quantity.value());
        }
        this.availableStock -= quantity.value();
    }
}

