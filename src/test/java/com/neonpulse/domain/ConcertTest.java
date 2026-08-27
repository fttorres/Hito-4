package com.neonpulse.domain;

import com.neonpulse.domain.exception.OutOfStockException;
import com.neonpulse.domain.entity.Concert;
import com.neonpulse.domain.valueobject.ConcertId;
import com.neonpulse.domain.valueobject.Money;
import com.neonpulse.domain.valueobject.Quantity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConcertTest {

    @Test
    @DisplayName("Concert disminuye el stock correctamente cuando hay disponibilidad")
    void testDecreaseStockSuccess() {
        Concert concert = new Concert(ConcertId.of("EVT-1"), "Concierto Rock", "2026-10-10", "ACTIVE", Money.of(50), 20);
        assertTrue(concert.hasEnoughStock(Quantity.of(5)));

        concert.decreaseStock(Quantity.of(5));
        assertEquals(15, concert.getAvailableStock());
    }

    @Test
    @DisplayName("Concert lanza OutOfStockException cuando se solicita más del disponible")
    void testDecreaseStockInsufficient() {
        Concert concert = new Concert(ConcertId.of("EVT-1"), "Concierto Rock", "2026-10-10", "ACTIVE", Money.of(50), 3);
        assertFalse(concert.hasEnoughStock(Quantity.of(5)));
        assertThrows(OutOfStockException.class, () -> concert.decreaseStock(Quantity.of(5)));
    }
}
