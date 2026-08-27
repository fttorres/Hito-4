package com.neonpulse.domain;

import com.neonpulse.domain.entity.ShoppingCart;
import com.neonpulse.domain.entity.TicketItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShoppingCartTest {

    @Test
    @DisplayName("Un carrito recién creado debe estar vacío y tener total 0")
    void shouldInitializeCartAsEmptyWithZeroTotal() {
        // Arrange
        ShoppingCart shoppingCart = new ShoppingCart();

        // Act
        BigDecimal actualTotal = shoppingCart.getTotal();
        boolean isListEmpty = shoppingCart.getItems().isEmpty();

        // Assert
        assertEquals(0, BigDecimal.ZERO.compareTo(actualTotal));
        assertTrue(isListEmpty);
    }

    @Test
    @DisplayName("El total del carrito debe ser la suma exacta de los subtotales de cada entrada")
    void shouldCalculateTotalAsSumOfItemSubtotals() {
        // Arrange
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(new TicketItem("Concierto NeonPulse", new BigDecimal("25000.50"), 2));
        shoppingCart.addItem(new TicketItem("Festival de Verano", new BigDecimal("15000.00"), 1));

        // Act
        BigDecimal actualTotal = shoppingCart.getTotal();

        // Assert
        BigDecimal expectedTotal = new BigDecimal("65001.00");
        assertEquals(0, expectedTotal.compareTo(actualTotal));
    }

    @Test
    @DisplayName("La lista devuelta por getItems debe ser inmutable")
    void shouldThrowExceptionWhenModifyingUnmodifiableItemList() {
        // Arrange
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(new TicketItem("Concerto VIP", new BigDecimal("100.00"), 1));
        List<TicketItem> items = shoppingCart.getItems();

        // Act & Assert
        assertThrows(UnsupportedOperationException.class, () ->
                items.add(new TicketItem("Concerto Extra", new BigDecimal("50.00"), 1))
        );
    }
}
