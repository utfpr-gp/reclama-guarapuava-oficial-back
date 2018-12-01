package br.edu.utfpr.reclamaguarapuava.model.service;

import br.edu.utfpr.reclamaguarapuava.model.Category;
import br.edu.utfpr.reclamaguarapuava.model.dto.CategoryDTO;
import br.edu.utfpr.reclamaguarapuava.model.repository.CategoryRepository;
import br.edu.utfpr.reclamaguarapuava.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<Category> findAll(Pageable pageable) {
        log.debug("executing query find all");
        return categoryRepository.findAll(pageable);
    }

    @Transactional
    public Response<Category> findById(Long id) {
        log.debug("executing query find by id: " + id);
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("category not found by id: " + id));
        return new Response<>(category);
    }

    @Transactional
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Transactional
    public Response<Category> save(CategoryDTO categoryDTO) {
        log.debug("executing statement insert");
        this.findByName(categoryDTO.getName()).ifPresent(c -> {
            throw new EntityExistsException("category with this name: " + c.getName() + " exist in database whit id: " + c.getId() + " created: " + c.getCreatedAt());
        });
        return new Response<>(categoryRepository.save(new Category(categoryDTO)));
    }

    @Transactional
    public Response<Category> update(CategoryDTO dto, Long id) {
        log.debug("executing statement alter");
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("category not found by id: " + dto.getId()));
        category.setName(dto.getName());
        return new Response<>(categoryRepository.save(category));
    }
}
