package com.neonpulse.domain.entity;

import com.neonpulse.domain.valueobject.ConcertId;
import com.neonpulse.domain.valueobject.Money;
import com.neonpulse.domain.valueobject.Quantity;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Entidad / Elemento que representa la línea de compra de un ticket.
 */
public class TicketItem {

    private final ConcertId concertId;
    private final String concertName;
    private final Money unitPrice;
    private final Quantity quantity;

    public TicketItem(ConcertId concertId, String concertName, Money unitPrice, Quantity quantity) {
        this.concertId = Objects.requireNonNull(concertId, "El ID del concerto no puede ser nulo");
        this.concertName = Objects.requireNonNull(concertName, "El nombre del concerto no puede ser nulo");
        this.unitPrice = Objects.requireNonNull(unitPrice, "El precio unitario no puede ser nulo");
        this.quantity = Objects.requireNonNull(quantity, "La cantidad no puede ser nula");
    }

    public TicketItem(String concertName, BigDecimal unitPrice, int quantity) {
        this(ConcertId.of(concertName), concertName, new Money(unitPrice), new Quantity(quantity));
    }

    public ConcertId getConcertId() {
        return concertId;
    }

    public String getConcertName() {
        return concertName;
    }

    public Money getUnitPrice() {
        return unitPrice;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPriceAmount() {
        return unitPrice.amount();
    }

    public int getQuantityValue() {
        return quantity.value();
    }

    public Money getSubtotal() {
        return unitPrice.multiply(quantity.value());
    }

    public BigDecimal getSubtotalAmount() {
        return getSubtotal().amount();
    }
}
