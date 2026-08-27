package com.neonpulse.domain;

import com.neonpulse.domain.exception.OutOfStockException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StockManagerTest {

    @Test
    @DisplayName("Debe lanzar OutOfStockException cuando el stock disponible es menor a la cantidad solicitada")
    void shouldThrowOutOfStockExceptionWhenStockIsInsufficient() {
        // Arrange
        int availableStock = 0;
        int requestedQuantity = 1;
        StockManager stockManager = new StockManager();

        // Act & Assert
        assertThrows(OutOfStockException.class, () -> {
            stockManager.checkAvailability(availableStock, requestedQuantity);
        });
    }

    @Test
    @DisplayName("No debe lanzar ninguna excepción cuando el stock disponible es suficiente")
    void shouldNotThrowExceptionWhenStockIsSufficient() {
        // Arrange
        int availableStock = 10;
        int requestedQuantity = 5;
        StockManager stockManager = new StockManager();

        // Act & Assert
        assertDoesNotThrow(() -> {
            stockManager.checkAvailability(availableStock, requestedQuantity);
        });
    }
}
