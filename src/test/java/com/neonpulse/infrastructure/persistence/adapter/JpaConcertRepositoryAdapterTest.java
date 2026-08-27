package com.neonpulse.infrastructure.persistence.adapter;

import com.neonpulse.domain.entity.Concert;
import com.neonpulse.domain.valueobject.ConcertId;
import com.neonpulse.domain.valueobject.Money;
import com.neonpulse.infrastructure.persistence.entity.ConcertEntity;
import com.neonpulse.infrastructure.persistence.repository.ConcertJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class JpaConcertRepositoryAdapterTest {

    @Mock
    private ConcertJpaRepository jpaRepository;

    private JpaConcertRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new JpaConcertRepositoryAdapter(jpaRepository);
    }

    @Test
    @DisplayName("findById mapea correctamente ConcertEntity a Concert de dominio")
    void testFindById() {
        ConcertEntity entity = new ConcertEntity("EVT-1", "Concierto Rock", "2026-10-10", "ACTIVE", BigDecimal.valueOf(100.0), 50);
        given(jpaRepository.findById("EVT-1")).willReturn(Optional.of(entity));

        Optional<Concert> concert = adapter.findById(ConcertId.of("EVT-1"));
        assertTrue(concert.isPresent());
        assertEquals("EVT-1", concert.get().getId().value());
        assertEquals("Concierto Rock", concert.get().getBand());
        assertEquals(50, concert.get().getAvailableStock());
    }

    @Test
    @DisplayName("save mapea correctamente Concert de dominio a ConcertEntity")
    void testSave() {
        Concert concert = new Concert(ConcertId.of("EVT-1"), "Concierto Rock", "2026-10-10", "ACTIVE", Money.of(100.0), 50);

        adapter.save(concert);

        ArgumentCaptor<ConcertEntity> captor = ArgumentCaptor.forClass(ConcertEntity.class);
        verify(jpaRepository).save(captor.capture());

        ConcertEntity savedEntity = captor.getValue();
        assertEquals("EVT-1", savedEntity.getId());
        assertEquals("Concierto Rock", savedEntity.getBand());
        assertEquals(BigDecimal.valueOf(100.0), savedEntity.getUnitPrice());
        assertEquals(50, savedEntity.getAvailableStock());
    }

    @Test
    @DisplayName("findAll mapea lista de entidades a dominio")
    void testFindAll() {
        given(jpaRepository.findAll()).willReturn(List.of(
                new ConcertEntity("EVT-1", "Rock", "2026-10-10", "ACTIVE", BigDecimal.valueOf(50.0), 20)
        ));

        List<Concert> concerts = adapter.findAll();
        assertEquals(1, concerts.size());
        assertEquals("EVT-1", concerts.get(0).getId().value());
    }
}
