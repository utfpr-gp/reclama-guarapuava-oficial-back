package br.edu.utfpr.reclamaguarapuava.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.reclamaguarapuava.model.City;

public interface CityRepository extends JpaRepository<City, Long>{
	Optional<City> findById(Long id);
}
