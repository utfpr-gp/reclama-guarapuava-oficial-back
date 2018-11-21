package br.edu.utfpr.reclamaguarapuava.occurrences.repositories;

import br.edu.utfpr.reclamaguarapuava.occurrences.entities.City;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends PagingAndSortingRepository<City, Long> {
    @Override
    Optional<City> findById(Long id);
}
