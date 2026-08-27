package com.neonpulse.application.usecase;

import com.neonpulse.domain.entity.ShoppingCart;
import com.neonpulse.domain.repository.ConcertRepository;
import com.neonpulse.domain.repository.PurchaseRepository;
import com.neonpulse.domain.valueobject.CardNumber;

import java.util.Objects;

/**
 * Caso de Uso aislado que orquesta la creación y procesamiento de compras.
 * Exige las dependencias de los repositorios a través de inyección por constructor (Cero uso de 'new').
 */
public class CreatePurchaseUseCase {

    private final ConcertRepository concertRepository;
    private final PurchaseRepository purchaseRepository;
    private final PaymentUseCase paymentUseCase;

    // Inyección obligatoria por constructor
    public CreatePurchaseUseCase(ConcertRepository concertRepository,
                                PurchaseRepository purchaseRepository,
                                PaymentUseCase paymentUseCase) {
        this.concertRepository = Objects.requireNonNull(concertRepository, "ConcertRepository no puede ser nulo");
        this.purchaseRepository = Objects.requireNonNull(purchaseRepository, "PurchaseRepository no puede ser nulo");
        this.paymentUseCase = Objects.requireNonNull(paymentUseCase, "PaymentUseCase no puede ser nulo");
    }

    public void execute(ShoppingCart cart, CardNumber cardNumber) {
        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("El carrito se encuentra vacío.");
        }
        paymentUseCase.processPayment(cardNumber, cart.getTotalMoney());
        this.purchaseRepository.save(cart, cardNumber);
    }
}
