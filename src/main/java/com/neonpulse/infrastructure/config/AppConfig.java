package com.neonpulse.infrastructure.config;

import com.neonpulse.application.service.PaymentService;
import com.neonpulse.application.service.PurchaseService;
import com.neonpulse.domain.entity.PurchaseValidator;
import com.neonpulse.domain.entity.StockManager;
import com.neonpulse.domain.repository.ConcertRepository;
import com.neonpulse.domain.repository.PurchaseRepository;
import com.neonpulse.infrastructure.notification.DummyNotifier;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de inyección de dependencias de Spring para los servicios de aplicación y repositorios.
 */
@Configuration
public class AppConfig {

    @Bean
    public DummyNotifier dummyNotifier() {
        return new DummyNotifier();
    }

    @Bean
    public PaymentService paymentService(DummyNotifier dummyNotifier) {
        com.neonpulse.application.port.MessageNotifier notifier = dummyNotifier;
        return new PaymentService(notifier);
    }

    @Bean
    public StockManager stockManager() {
        return new StockManager();
    }

    @Bean
    public PurchaseValidator purchaseValidator() {
        return new PurchaseValidator();
    }

    @Bean
    public PurchaseService purchaseService(StockManager stockManager,
                                           PurchaseValidator purchaseValidator,
                                           PaymentService paymentService,
                                           ConcertRepository concertRepository,
                                           PurchaseRepository purchaseRepository) {
        return new PurchaseService(
                stockManager,
                purchaseValidator,
                paymentService,
                concertRepository,
                purchaseRepository
        );
    }
}
