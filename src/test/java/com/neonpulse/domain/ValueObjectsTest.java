package com.neonpulse.domain;

import com.neonpulse.domain.exception.InvalidQuantityException;
import com.neonpulse.domain.valueobject.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ValueObjectsTest {

    @Test
    @DisplayName("Money no permite valores negativos o nulos")
    void testMoneyValidation() {
        assertThrows(NullPointerException.class, () -> new Money(null));
        assertThrows(IllegalArgumentException.class, () -> new Money(new BigDecimal("-10")));

        Money m1 = Money.of(100);
        Money m2 = Money.of(50);
        assertEquals(new BigDecimal("150"), m1.add(m2).amount());
        assertEquals(new BigDecimal("300"), m1.multiply(3).amount());
    }

    @Test
    @DisplayName("Quantity auto-valida límites en constructor compacto")
    void testQuantityValidation() {
        assertThrows(InvalidQuantityException.class, () -> new Quantity(0));
        assertThrows(InvalidQuantityException.class, () -> new Quantity(-5));
        assertThrows(InvalidQuantityException.class, () -> new Quantity(11));

        Quantity q = new Quantity(5);
        assertEquals(5, q.value());
    }

    @Test
    @DisplayName("CardNumber valida formato y extrae últimos 4 dígitos")
    void testCardNumber() {
        assertThrows(IllegalArgumentException.class, () -> new CardNumber(null));
        assertThrows(IllegalArgumentException.class, () -> new CardNumber("   "));

        CardNumber card = CardNumber.of("4532123456788888");
        assertEquals("8888", card.getLast4Digits());
    }

    @Test
    @DisplayName("ConcertId e TicketId validan identificadores no vacíos")
    void testIdentifiers() {
        assertThrows(IllegalArgumentException.class, () -> new ConcertId(""));
        assertThrows(IllegalArgumentException.class, () -> new TicketId(""));
        assertEquals("EVT-100", ConcertId.of("EVT-100").value());
    }
}
