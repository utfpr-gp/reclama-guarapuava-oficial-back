package br.edu.utfpr.reclamaguarapuava.model.repository;

import br.edu.utfpr.reclamaguarapuava.model.Occurrence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OccurrenceRepository extends JpaRepository<Occurrence, Long> {

    List<Occurrence> findAllByProblem_CategoryId(Long id);

    List<Occurrence> findAllByProblemId(Long id);
}
