package com.neonpulse.infrastructure.persistence.adapter;

import com.neonpulse.domain.entity.ShoppingCart;
import com.neonpulse.domain.entity.TicketItem;
import com.neonpulse.domain.valueobject.CardNumber;
import com.neonpulse.domain.valueobject.ConcertId;
import com.neonpulse.domain.valueobject.Money;
import com.neonpulse.domain.valueobject.Quantity;
import com.neonpulse.infrastructure.persistence.entity.PurchaseEntity;
import com.neonpulse.infrastructure.persistence.repository.PurchaseJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class JpaPurchaseRepositoryAdapterTest {

    @Mock
    private PurchaseJpaRepository jpaRepository;

    private JpaPurchaseRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new JpaPurchaseRepositoryAdapter(jpaRepository);
    }

    @Test
    @DisplayName("save mapea correctamente ShoppingCart y CardNumber a PurchaseEntity")
    void testSave() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new TicketItem(
                ConcertId.of("EVT-100"),
                "Festival Lollapalooza",
                Money.of(150.0),
                Quantity.of(2)
        ));
        CardNumber cardNumber = CardNumber.of("1234567890123456");

        adapter.save(cart, cardNumber);

        ArgumentCaptor<PurchaseEntity> captor = ArgumentCaptor.forClass(PurchaseEntity.class);
        verify(jpaRepository).save(captor.capture());

        PurchaseEntity saved = captor.getValue();
        assertEquals("3456", saved.getCardLast4());
        assertEquals(BigDecimal.valueOf(300.0), saved.getTotalAmount());
        assertEquals(2, saved.getTotalQuantity());
        assertEquals(1, saved.getItems().size());
        assertEquals("EVT-100", saved.getItems().get(0).getConcertId());
        assertEquals(2, saved.getItems().get(0).getQuantity());
    }
}
