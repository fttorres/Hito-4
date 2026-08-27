package com.neonpulse.application;

import com.neonpulse.application.usecase.PaymentUseCase;
import com.neonpulse.application.usecase.PurchaseUseCase;
import com.neonpulse.domain.entity.Concert;
import com.neonpulse.domain.entity.PurchaseValidator;
import com.neonpulse.domain.entity.ShoppingCart;
import com.neonpulse.domain.entity.StockManager;
import com.neonpulse.domain.entity.TicketItem;
import com.neonpulse.domain.valueobject.CardNumber;
import com.neonpulse.domain.valueobject.ConcertId;
import com.neonpulse.domain.valueobject.Money;
import com.neonpulse.domain.valueobject.Quantity;
import com.neonpulse.infrastructure.notification.DummyNotifier;
import com.neonpulse.infrastructure.persistence.InMemoryConcertRepository;
import com.neonpulse.infrastructure.persistence.InMemoryPurchaseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseUseCaseTest {

    @Test
    @DisplayName("Ejecuta caso de uso completo de compra desacoplado por repositorios")
    void testCompletePurchaseFlow() {
        // Given
        InMemoryConcertRepository concertRepo = new InMemoryConcertRepository();
        InMemoryPurchaseRepository purchaseRepo = new InMemoryPurchaseRepository();
        DummyNotifier notifier = new DummyNotifier();
        PaymentUseCase paymentUseCase = new PaymentUseCase(notifier);

        ConcertId concertId = ConcertId.of("EVT-ROCK");
        Concert concert = new Concert(concertId, "Rock Concert", "2026-10-10", "ACTIVE", Money.of(40), 10);
        concertRepo.save(concert);

        PurchaseUseCase purchaseUseCase = new PurchaseUseCase(
                new StockManager(),
                new PurchaseValidator(),
                paymentUseCase,
                concertRepo,
                purchaseRepo
        );

        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new TicketItem(concertId, "Rock Concert", Money.of(40), Quantity.of(3)));

        // When
        purchaseUseCase.processPurchase(cart, CardNumber.of("4532111122223333"));

        // Then
        // 1. Stock actualizado en repositorio
        Concert updatedConcert = concertRepo.findById(concertId).orElseThrow();
        assertEquals(7, updatedConcert.getAvailableStock());

        // 2. Compra registrada en repositorio de compras
        assertEquals(1, purchaseRepo.count());

        // 3. Notificación enviada
        assertEquals("4532111122223333", notifier.getLastRecipient());
        assertTrue(notifier.getLastMessage().contains("3333"));
    }
}
