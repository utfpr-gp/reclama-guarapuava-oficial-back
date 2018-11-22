package br.edu.utfpr.reclamaguarapuava.model.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.reclamaguarapuava.model.Occurrence;

public interface OccurrenceRepository extends JpaRepository<Occurrence, Long>{
	Page<Occurrence> findAllByUserId(Long id, Pageable pageable);
    List<Occurrence> findAllByProblem_CategoryId(Long id);
    List<Occurrence> findAllByProblemId(Long id);
}
