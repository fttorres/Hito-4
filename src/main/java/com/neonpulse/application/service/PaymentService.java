package com.neonpulse.application.service;

import com.neonpulse.application.port.MessageNotifier;
import com.neonpulse.application.usecase.PaymentUseCase;

/**
 * Servicio de aplicación que extiende del caso de uso PaymentUseCase.
 */
public class PaymentService extends PaymentUseCase {

    public PaymentService(MessageNotifier... notifiers) {
        super(notifiers);
    }

}
