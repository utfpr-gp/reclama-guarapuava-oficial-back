package br.edu.utfpr.reclamaguarapuava.occurrences.repositories;

import br.edu.utfpr.reclamaguarapuava.occurrences.entities.Neighborhood;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NeighborhoodRepository extends CrudRepository<Neighborhood, Long> {
}
