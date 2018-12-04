package br.edu.utfpr.reclamaguarapuava.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.utfpr.reclamaguarapuava.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

	Optional<City> findByName(String name);

	List<City> findAllByName(String name);
}
