package com.neonpulse.domain.entity;

import com.neonpulse.domain.valueobject.Money;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Agregado Raíz (Aggregate Root) del Carrito de Compras optimizado.
 */
public class ShoppingCart {

    private final List<TicketItem> items = new ArrayList<>();

    public void addItem(TicketItem item) {
        if (item != null) {
            items.add(item);
        }
    }

    public List<TicketItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public Money getTotalMoney() {
        return items.stream()
                .map(TicketItem::getSubtotal)
                .reduce(Money.ZERO, Money::add);
    }

    public BigDecimal getTotal() {
        return getTotalMoney().amount();
    }

    public int getTotalQuantity() {
        return items.stream()
                .mapToInt(item -> item.getQuantity().value())
                .sum();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }
}
