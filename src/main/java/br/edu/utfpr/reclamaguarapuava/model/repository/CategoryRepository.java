package br.edu.utfpr.reclamaguarapuava.model.repository;

import br.edu.utfpr.reclamaguarapuava.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    @Query("select categories from Category categories where categories.name like %:name%")
    List<Category> findAllByName(@Param("name") String name);
}
