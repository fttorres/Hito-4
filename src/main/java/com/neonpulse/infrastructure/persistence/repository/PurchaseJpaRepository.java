package com.neonpulse.infrastructure.persistence.repository;

import com.neonpulse.infrastructure.persistence.entity.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio Spring Data JPA para la entidad PurchaseEntity.
 */
@Repository
public interface PurchaseJpaRepository extends JpaRepository<PurchaseEntity, Long> {
}
