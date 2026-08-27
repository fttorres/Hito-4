package com.neonpulse.infrastructure.persistence.adapter;

import com.neonpulse.domain.entity.Concert;
import com.neonpulse.domain.repository.ConcertRepository;
import com.neonpulse.domain.valueobject.ConcertId;
import com.neonpulse.domain.valueobject.Money;
import com.neonpulse.infrastructure.persistence.entity.ConcertEntity;
import com.neonpulse.infrastructure.persistence.repository.ConcertJpaRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador de infraestructura que implementa el puerto ConcertRepository
 * conectando el dominio puro con la persistencia relacional Spring Data JPA.
 */
@Repository
@Primary
public class JpaConcertRepositoryAdapter implements ConcertRepository {

    private final ConcertJpaRepository jpaRepository;

    public JpaConcertRepositoryAdapter(ConcertJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Concert> findById(ConcertId id) {
        if (id == null) {
            return Optional.empty();
        }
        return jpaRepository.findById(id.value())
                .map(this::toDomain);
    }

    @Override
    public List<Concert> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Concert concert) {
        if (concert == null) {
            return;
        }
        ConcertEntity entity = toEntity(concert);
        jpaRepository.save(entity);
    }

    @Override
    public void deleteById(ConcertId id) {
        if (id != null) {
            jpaRepository.deleteById(id.value());
        }
    }

    private Concert toDomain(ConcertEntity entity) {
        return new Concert(
                ConcertId.of(entity.getId()),
                entity.getBand(),
                entity.getDate(),
                entity.getStatus(),
                Money.of(entity.getUnitPrice()),
                entity.getAvailableStock()
        );
    }

    private ConcertEntity toEntity(Concert concert) {
        return new ConcertEntity(
                concert.getId().value(),
                concert.getBand(),
                concert.getDate(),
                concert.getStatus(),
                concert.getUnitPrice().amount(),
                concert.getAvailableStock()
        );
    }
}
