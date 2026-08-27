package com.neonpulse.infrastructure.persistence.repository;

import com.neonpulse.infrastructure.persistence.entity.PurchaseEntity;
import com.neonpulse.infrastructure.persistence.entity.PurchaseItemEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class PurchaseJpaRepositoryTest {

    @Autowired
    private PurchaseJpaRepository purchaseJpaRepository;

    @Test
    @DisplayName("PurchaseJpaRepository persiste PurchaseEntity con sus PurchaseItemEntity en cascada")
    void testSavePurchaseWithItems() {
        PurchaseEntity purchase = new PurchaseEntity(
                "3456",
                BigDecimal.valueOf(300.0),
                2,
                LocalDateTime.now()
        );

        PurchaseItemEntity item = new PurchaseItemEntity(
                "EVT-100",
                "Lollapalooza",
                BigDecimal.valueOf(150.0),
                2
        );
        purchase.addItem(item);

        PurchaseEntity saved = purchaseJpaRepository.save(purchase);
        assertNotNull(saved.getId());

        Optional<PurchaseEntity> found = purchaseJpaRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("3456", found.get().getCardLast4());
        assertEquals(1, found.get().getItems().size());
        assertEquals("EVT-100", found.get().getItems().get(0).getConcertId());
    }
}
