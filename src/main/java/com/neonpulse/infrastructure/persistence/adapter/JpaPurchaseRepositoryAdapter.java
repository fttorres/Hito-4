package com.neonpulse.infrastructure.persistence.adapter;

import com.neonpulse.domain.entity.ShoppingCart;
import com.neonpulse.domain.entity.TicketItem;
import com.neonpulse.domain.repository.PurchaseRepository;
import com.neonpulse.domain.valueobject.CardNumber;
import com.neonpulse.infrastructure.persistence.entity.PurchaseEntity;
import com.neonpulse.infrastructure.persistence.entity.PurchaseItemEntity;
import com.neonpulse.infrastructure.persistence.repository.PurchaseJpaRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Adaptador de infraestructura que implementa el puerto PurchaseRepository
 * conectando el dominio puro con la persistencia relacional Spring Data JPA.
 */
@Repository
@Primary
public class JpaPurchaseRepositoryAdapter implements PurchaseRepository {

    private final PurchaseJpaRepository jpaRepository;

    public JpaPurchaseRepositoryAdapter(PurchaseJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(ShoppingCart cart, CardNumber cardNumber) {
        Objects.requireNonNull(cart, "El carrito no puede ser nulo");
        Objects.requireNonNull(cardNumber, "El número de tarjeta no puede ser nulo");

        PurchaseEntity purchaseEntity = new PurchaseEntity(
                cardNumber.getLast4Digits(),
                cart.getTotalMoney().amount(),
                cart.getTotalQuantity(),
                LocalDateTime.now()
        );

        for (TicketItem item : cart.getItems()) {
            PurchaseItemEntity itemEntity = new PurchaseItemEntity(
                    item.getConcertId().value(),
                    item.getConcertName(),
                    item.getUnitPrice().amount(),
                    item.getQuantity().value()
            );
            purchaseEntity.addItem(itemEntity);
        }

        jpaRepository.save(purchaseEntity);
    }
}
