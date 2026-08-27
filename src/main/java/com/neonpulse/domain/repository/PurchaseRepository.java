package com.neonpulse.domain.repository;

import com.neonpulse.domain.entity.ShoppingCart;
import com.neonpulse.domain.valueobject.CardNumber;

/**
 * Contrato de repositorio puro dentro del dominio para registrar Compras.
 */
public interface PurchaseRepository {
    void save(ShoppingCart cart, CardNumber cardNumber);
}
