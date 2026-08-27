package com.neonpulse.domain.valueobject;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Value Object inmutable para representación financiera exacta.
 */
public record Money(BigDecimal amount) {

    public static final Money ZERO = new Money(BigDecimal.ZERO);

    public Money {
        Objects.requireNonNull(amount, "El monto no puede ser nulo");
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El monto no puede ser negativo: " + amount);
        }
    }

    public static Money of(BigDecimal amount) {
        return new Money(amount);
    }

    public static Money of(double value) {
        return new Money(BigDecimal.valueOf(value));
    }

    public static Money of(long value) {
        return new Money(BigDecimal.valueOf(value));
    }

    public Money add(Money other) {
        Objects.requireNonNull(other, "El monto a sumar no puede ser nulo");
        return new Money(this.amount.add(other.amount()));
    }

    public Money multiply(int factor) {
        if (factor < 0) {
            throw new IllegalArgumentException("El factor no puede ser negativo: " + factor);
        }
        return new Money(this.amount.multiply(BigDecimal.valueOf(factor)));
    }
}
