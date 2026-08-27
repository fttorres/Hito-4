package com.neonpulse.infrastructure.persistence.repository;

import com.neonpulse.infrastructure.persistence.entity.ConcertEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio Spring Data JPA para la entidad ConcertEntity.
 * Resuelve automáticamente las operaciones CRUD de base de datos relacional.
 */
@Repository
public interface ConcertJpaRepository extends JpaRepository<ConcertEntity, String> {
}
