package com.neonpulse.domain;

import com.neonpulse.domain.exception.InvalidQuantityException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PurchaseValidatorTest {

    @ParameterizedTest(name = "cantidad inválida = {0}")
    @ValueSource(ints = {0, -1, -5, 11, 100})
    @DisplayName("Debe lanzar InvalidQuantityException para cantidades fuera de rango")
    void shouldThrownInvalidQuantityExceptionForInvalidValues(int invalidQuantity) {
        // Arrange (Preparar)
        PurchaseValidator purchaseValidator = new PurchaseValidator();

        // Act & Assert (Ejecutar y Verificar)
        // Gracias a @ParameterizedTest, este método se ejecuta una vez por
        // cada valor declarado en @ValueSource, invocando
        // processQuantity(invalidQuantity) sobre la misma instancia de
        // PurchaseValidator en cada iteración.
        assertThrows(InvalidQuantityException.class, () -> {
            purchaseValidator.processQuantity(invalidQuantity);
        });
    }
}
