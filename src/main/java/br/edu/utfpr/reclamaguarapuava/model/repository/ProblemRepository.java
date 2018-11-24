package br.edu.utfpr.reclamaguarapuava.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.reclamaguarapuava.model.Category;
import br.edu.utfpr.reclamaguarapuava.model.Problem;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    Optional<Problem> findByName(String name);

    List<Problem> deleteAllByCategoryId(Long id);

    List<Problem> findAllByName(String name);
}
