package br.edu.utfpr.reclamaguarapuava.model.repository;

import br.edu.utfpr.reclamaguarapuava.model.Category;
import br.edu.utfpr.reclamaguarapuava.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    @Query("delete from Problem problems where problems.category.id = :id")
    List<Problem> deleteAllByCategoryId(@Param("id") Long id);

    Optional<Problem> findByName(String name);

    @Query("select problems from Problem problems where problems.name like %:name%")
    List<Category> findAllByName(@Param("name") String name);
}
