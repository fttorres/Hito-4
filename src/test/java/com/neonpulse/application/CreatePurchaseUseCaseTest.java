package com.neonpulse.application;

import com.neonpulse.application.usecase.CreatePurchaseUseCase;
import com.neonpulse.application.usecase.PaymentUseCase;
import com.neonpulse.domain.entity.ShoppingCart;
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

class CreatePurchaseUseCaseTest {

    @Test
    @DisplayName("CreatePurchaseUseCase ejecuta flujo exigiendo inyección por constructor de repositorios")
    void testCreatePurchaseUseCaseExecute() {
        InMemoryConcertRepository concertRepo = new InMemoryConcertRepository();
        InMemoryPurchaseRepository purchaseRepo = new InMemoryPurchaseRepository();
        DummyNotifier notifier = new DummyNotifier();
        PaymentUseCase paymentUseCase = new PaymentUseCase(notifier);

        CreatePurchaseUseCase useCase = new CreatePurchaseUseCase(concertRepo, purchaseRepo, paymentUseCase);

        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new TicketItem(ConcertId.of("EVT-1"), "Concierto", Money.of(50), Quantity.of(2)));

        useCase.execute(cart, CardNumber.of("1234123412341234"));

        assertEquals(1, purchaseRepo.count());
    }

    @Test
    @DisplayName("Lanza IllegalStateException si el carrito está vacío")
    void testEmptyCartFails() {
        InMemoryConcertRepository concertRepo = new InMemoryConcertRepository();
        InMemoryPurchaseRepository purchaseRepo = new InMemoryPurchaseRepository();
        DummyNotifier notifier = new DummyNotifier();
        PaymentUseCase paymentUseCase = new PaymentUseCase(notifier);

        CreatePurchaseUseCase useCase = new CreatePurchaseUseCase(concertRepo, purchaseRepo, paymentUseCase);

        ShoppingCart cart = new ShoppingCart();
        assertThrows(IllegalStateException.class, () -> useCase.execute(cart, CardNumber.of("1234123412341234")));
    }
}
