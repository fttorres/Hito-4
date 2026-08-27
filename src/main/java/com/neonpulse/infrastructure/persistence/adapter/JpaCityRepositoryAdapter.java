package com.neonpulse.infrastructure.persistence.adapter;

import com.neonpulse.domain.entity.City;
import com.neonpulse.domain.repository.CityRepository;
import com.neonpulse.infrastructure.persistence.entity.CityEntity;
import com.neonpulse.infrastructure.persistence.repository.CityJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaCityRepositoryAdapter implements CityRepository {

    private final CityJpaRepository repository;

    public JpaCityRepositoryAdapter(CityJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(City city) {
        CityEntity entity = new CityEntity(city.id(), city.name());
        repository.save(entity);
    }

    @Override
    public Optional<City> findById(String id) {
        return repository.findById(id)
                .map(entity -> new City(entity.getId(), entity.getName()));
    }

    @Override
    public List<City> findAll() {
        return repository.findAll().stream()
                .map(entity -> new City(entity.getId(), entity.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
