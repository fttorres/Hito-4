package com.neonpulse.application.service;

import com.neonpulse.application.usecase.PaymentUseCase;
import com.neonpulse.application.usecase.PurchaseUseCase;
import com.neonpulse.domain.entity.PurchaseValidator;
import com.neonpulse.domain.entity.StockManager;
import com.neonpulse.domain.repository.ConcertRepository;
import com.neonpulse.domain.repository.PurchaseRepository;

/**
 * Servicio de aplicación que extiende del caso de uso PurchaseUseCase.
 */
public class PurchaseService extends PurchaseUseCase {

    public PurchaseService(StockManager stockManager,
                           PurchaseValidator purchaseValidator,
                           PaymentUseCase paymentUseCase,
                           ConcertRepository concertRepository,
                           PurchaseRepository purchaseRepository) {
        super(stockManager, purchaseValidator, paymentUseCase, concertRepository, purchaseRepository);
    }

    public PurchaseService(StockManager stockManager,
                           PurchaseValidator purchaseValidator,
                           PaymentUseCase paymentUseCase) {
        super(stockManager, purchaseValidator, paymentUseCase);
    }
}
