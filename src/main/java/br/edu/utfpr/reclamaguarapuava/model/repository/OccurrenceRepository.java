package br.edu.utfpr.reclamaguarapuava.model.repository;

import br.edu.utfpr.reclamaguarapuava.model.Occurrence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OccurrenceRepository extends JpaRepository<Occurrence, Long> {

    @Query("SELECT occurrences from Occurrence occurrences where occurrences.problem.category.id = ?1")
    List<Occurrence> findByCategory(Long id);
}
