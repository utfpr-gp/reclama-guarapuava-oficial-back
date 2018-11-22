package br.edu.utfpr.reclamaguarapuava.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.reclamaguarapuava.model.Occurrence;

public interface OccurrenceRepository extends JpaRepository<Occurrence, Long>{
	Page<Occurrence> findAllByUserId(Long id, Pageable pageable);
}
