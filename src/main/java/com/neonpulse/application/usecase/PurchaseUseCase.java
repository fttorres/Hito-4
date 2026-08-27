package com.neonpulse.application.usecase;

import com.neonpulse.domain.entity.Concert;
import com.neonpulse.domain.entity.PurchaseValidator;
import com.neonpulse.domain.entity.ShoppingCart;
import com.neonpulse.domain.entity.StockManager;
import com.neonpulse.domain.entity.TicketItem;
import com.neonpulse.domain.exception.ConcertNotFoundException;
import com.neonpulse.domain.repository.ConcertRepository;
import com.neonpulse.domain.repository.PurchaseRepository;
import com.neonpulse.domain.valueobject.CardNumber;
import com.neonpulse.domain.valueobject.Money;

import java.util.Objects;

/**
 * Caso de Uso principal que orquesta la compra de tickets de concertos.
 * Inyecta únicamente las abstracciones/interfaces de repositorios y notificadores por constructor.
 */
public class PurchaseUseCase {

    private final StockManager stockManager;
    private final PurchaseValidator purchaseValidator;
    private final PaymentUseCase paymentUseCase;
    private final ConcertRepository concertRepository;
    private final PurchaseRepository purchaseRepository;

    public PurchaseUseCase(StockManager stockManager,
                           PurchaseValidator purchaseValidator,
                           PaymentUseCase paymentUseCase,
                           ConcertRepository concertRepository,
                           PurchaseRepository purchaseRepository) {
        this.stockManager = Objects.requireNonNull(stockManager, "StockManager no puede ser nulo");
        this.purchaseValidator = Objects.requireNonNull(purchaseValidator, "PurchaseValidator no puede ser nulo");
        this.paymentUseCase = Objects.requireNonNull(paymentUseCase, "PaymentUseCase no puede ser nulo");
        this.concertRepository = concertRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public PurchaseUseCase(StockManager stockManager,
                           PurchaseValidator purchaseValidator,
                           PaymentUseCase paymentUseCase) {
        this(stockManager, purchaseValidator, paymentUseCase, null, null);
    }

    public void execute(ShoppingCart cart, CardNumber cardNumber) {
        processPurchase(cart, cardNumber);
    }

    public void processPurchase(ShoppingCart cart, CardNumber cardNumber) {
        Objects.requireNonNull(cart, "El carrito no puede ser nulo");
        Objects.requireNonNull(cardNumber, "El medio de pago no puede ser nulo");

        int totalQuantity = cart.getTotalQuantity();
        purchaseValidator.processQuantity(totalQuantity);

        if (concertRepository != null) {
            for (TicketItem item : cart.getItems()) {
                Concert concert = concertRepository.findById(item.getConcertId())
                        .orElseThrow(() -> new ConcertNotFoundException("Concerto no encontrado: " + item.getConcertId().value()));
                concert.decreaseStock(item.getQuantity());
                concertRepository.save(concert);
            }
        }

        Money total = cart.getTotalMoney();
        paymentUseCase.processPayment(cardNumber, total);

        if (purchaseRepository != null) {
            purchaseRepository.save(cart, cardNumber);
        }
    }

    public void processPurchase(ShoppingCart cart, int availableStock, String cardNumber) {
        int totalQuantity = cart.getTotalQuantity();

        purchaseValidator.processQuantity(totalQuantity);
        stockManager.checkAvailability(availableStock, totalQuantity);

        paymentUseCase.processPayment(cardNumber, cart.getTotal());

        if (purchaseRepository != null) {
            purchaseRepository.save(cart, CardNumber.of(cardNumber));
        }
    }
}
