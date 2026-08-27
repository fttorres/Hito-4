package com.neonpulse.domain.repository;

import com.neonpulse.domain.entity.City;

import java.util.List;
import java.util.Optional;

public interface CityRepository {
    void save(City city);
    Optional<City> findById(String id);
    List<City> findAll();
    void deleteById(String id);
}
