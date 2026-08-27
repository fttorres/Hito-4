package com.neonpulse.infrastructure;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.neonpulse.domain.entity.Concert;
import com.neonpulse.domain.entity.ShoppingCart;
import com.neonpulse.domain.entity.TicketItem;
import com.neonpulse.domain.valueobject.CardNumber;
import com.neonpulse.domain.valueobject.ConcertId;
import com.neonpulse.domain.valueobject.Money;
import com.neonpulse.domain.valueobject.Quantity;
import com.neonpulse.infrastructure.persistence.InMemoryConcertRepository;
import com.neonpulse.infrastructure.persistence.InMemoryPurchaseRepository;

class InMemoryRepositoriesTest {

    @Test //Verifica guardar y consultar concertos por ID en `InMemoryConcertRepository`.
    @DisplayName("InMemoryConcertRepository permite guardar y buscar por ID")
    void testConcertRepository() {
        InMemoryConcertRepository repo = new InMemoryConcertRepository();
        Concert concert = new Concert(ConcertId.of("EVT-100"), "Festival Lollapalooza", "2026-10-10", "ACTIVE", Money.of(150), 100);

        repo.save(concert);

        Optional<Concert> found = repo.findById(ConcertId.of("EVT-100"));
        assertTrue(found.isPresent());
        assertEquals("Festival Lollapalooza", found.get().getBand());
        assertEquals(1, repo.count());
    }

    @Test //Verifica guardar registros de compra y consultar su histórico en `InMemoryPurchaseRepository`.
    @DisplayName("InMemoryPurchaseRepository guarda correctamente los registros de compra")
    void testPurchaseRepository() {
        InMemoryPurchaseRepository repo = new InMemoryPurchaseRepository();
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new TicketItem(ConcertId.of("EVT-100"), "Festival Lollapalooza", Money.of(150), Quantity.of(2)));

        repo.save(cart, CardNumber.of("1234567890123456"));

        assertEquals(1, repo.count());
        assertEquals("3456", repo.getRecords().get(0).cardNumber().getLast4Digits());
    }
}
