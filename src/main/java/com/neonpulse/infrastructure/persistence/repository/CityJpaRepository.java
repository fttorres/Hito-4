package com.neonpulse.infrastructure.persistence.repository;

import com.neonpulse.infrastructure.persistence.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityJpaRepository extends JpaRepository<CityEntity, String> {
}
