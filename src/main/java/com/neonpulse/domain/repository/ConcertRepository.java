package com.neonpulse.domain.repository;

import com.neonpulse.domain.entity.Concert;
import com.neonpulse.domain.valueobject.ConcertId;

import java.util.Optional;

/**
 * Contrato de repositorio puro dentro del dominio para la gestión de Concertos.
 */
public interface ConcertRepository {
    Optional<Concert> findById(ConcertId id);
    java.util.List<Concert> findAll();
    void save(Concert concert);
    void deleteById(ConcertId id);
}
