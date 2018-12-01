package br.edu.utfpr.reclamaguarapuava.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.reclamaguarapuava.model.Category;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

    Optional<Category> findByName(String name);

    List<Category> findAllByName(String name);
}
