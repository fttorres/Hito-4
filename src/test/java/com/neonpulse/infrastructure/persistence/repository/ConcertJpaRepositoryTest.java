package com.neonpulse.infrastructure.persistence.repository;

import com.neonpulse.infrastructure.persistence.entity.ConcertEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ConcertJpaRepositoryTest {

    @Autowired
    private ConcertJpaRepository concertJpaRepository;

    @Test
    @DisplayName("ConcertJpaRepository guarda y recupera una entidad ConcertEntity correctamente")
    void testSaveAndFindById() {
        ConcertEntity entity = new ConcertEntity("EVT-100", "Lollapalooza", "2026-10-10", "ACTIVE", BigDecimal.valueOf(150.0), 50);
        concertJpaRepository.save(entity);

        Optional<ConcertEntity> found = concertJpaRepository.findById("EVT-100");
        assertTrue(found.isPresent());
        assertEquals("Lollapalooza", found.get().getBand());
        assertEquals(BigDecimal.valueOf(150.0).stripTrailingZeros(), found.get().getUnitPrice().stripTrailingZeros());
        assertEquals(50, found.get().getAvailableStock());
    }

    @Test
    @DisplayName("ConcertJpaRepository lista y elimina entidades correctamente")
    void testFindAllAndDelete() {
        concertJpaRepository.save(new ConcertEntity("EVT-1", "Concierto 1", "2026-10-10", "ACTIVE", BigDecimal.valueOf(50.0), 20));
        concertJpaRepository.save(new ConcertEntity("EVT-2", "Concierto 2", "2026-10-10", "ACTIVE", BigDecimal.valueOf(80.0), 30));

        List<ConcertEntity> all = concertJpaRepository.findAll();
        assertEquals(2, all.size());

        concertJpaRepository.deleteById("EVT-1");
        Optional<ConcertEntity> deleted = concertJpaRepository.findById("EVT-1");
        assertFalse(deleted.isPresent());
        assertEquals(1, concertJpaRepository.findAll().size());
    }
}
