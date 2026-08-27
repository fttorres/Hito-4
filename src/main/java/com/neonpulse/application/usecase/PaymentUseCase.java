package com.neonpulse.application.usecase;

import com.neonpulse.application.port.MessageNotifier;

import com.neonpulse.domain.valueobject.CardNumber;
import com.neonpulse.domain.valueobject.Money;

import java.math.BigDecimal;
import java.util.List;

/**
 * Caso de Uso: Procesamiento de Pago.
 */
public class PaymentUseCase {

    private final List<MessageNotifier> notifiers;

    public PaymentUseCase(MessageNotifier... notifiers) {
        this.notifiers = List.of(notifiers);
    }

    public PaymentUseCase(List<MessageNotifier> notifiers) {
        this.notifiers = List.copyOf(notifiers);
    }

    public void processPayment(CardNumber cardNumber, Money amount) {
        if (cardNumber == null) {
            throw new IllegalArgumentException("El número de tarjeta no puede estar vacío");
        }
        if (amount == null || amount.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto a pagar debe ser mayor a cero");
        }

        String last4Digits = cardNumber.getLast4Digits();
        String message = "Pago de $" + amount.amount() + " procesado con tarjeta terminada en " + last4Digits;

        for (MessageNotifier notifier : notifiers) {
            notifier.sendNotification(cardNumber.value(), message);
        }
    }

    public void processPayment(String cardNumberStr, BigDecimal amountVal) {
        CardNumber card = CardNumber.of(cardNumberStr);
        Money money = new Money(amountVal);
        processPayment(card, money);
    }
}
