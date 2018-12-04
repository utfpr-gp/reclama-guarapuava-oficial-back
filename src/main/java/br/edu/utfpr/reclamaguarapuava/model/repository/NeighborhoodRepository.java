package br.edu.utfpr.reclamaguarapuava.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.utfpr.reclamaguarapuava.model.Neighborhood;

@Repository
public interface NeighborhoodRepository extends JpaRepository<Neighborhood, Long> {

	Optional<Neighborhood> findByName(String name);

	List<Neighborhood> findAllByName(String name);
}
