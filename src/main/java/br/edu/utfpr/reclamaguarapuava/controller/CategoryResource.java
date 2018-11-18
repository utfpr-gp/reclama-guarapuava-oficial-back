package br.edu.utfpr.reclamaguarapuava.controller;

import br.edu.utfpr.reclamaguarapuava.model.Category;
import br.edu.utfpr.reclamaguarapuava.model.dto.CategoryDTO;
import br.edu.utfpr.reclamaguarapuava.model.repository.CategoryRepository;
import br.edu.utfpr.reclamaguarapuava.service.CategoryService;
import br.edu.utfpr.reclamaguarapuava.service.OccurrenceService;
import br.edu.utfpr.reclamaguarapuava.service.ProblemService;
import br.edu.utfpr.reclamaguarapuava.util.Response;
import org.hibernate.ObjectDeletedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/admin/categorias")
@CrossOrigin(origins = "*")
public class CategoryResource {

    private static final Logger log = LoggerFactory.getLogger(CategoryResource.class);

    @Autowired
    CategoryService categoryService;

    @Autowired
    OccurrenceService occurrenceService;

    @Autowired
    ProblemService problemService;

    @GetMapping
    public ResponseEntity<Response<Page<Category>>> index(Pageable pageable) {
        Response<Page<Category>> response = new Response<>();
        response.setData(categoryService.findAll(pageable));
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<CategoryDTO>> getById(@PathVariable Long id) {
        Response<CategoryDTO> response = new Response<>();

        Optional<Category> category = categoryService.findById(id);

        if (!category.isPresent()) {
            response.addError("Categoria não encontrada");
            return ResponseEntity.badRequest().body(response);
        }

        CategoryDTO categoryDTO = new CategoryDTO(category);

        response.setData(categoryDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<CategoryDTO>> store(@Valid @RequestBody CategoryDTO dto, BindingResult result) {
        Response<CategoryDTO> response = new Response<>();

        if (result.hasErrors()) {
            response.setErrors(result);
            return ResponseEntity.badRequest().body(response);
        }

        Optional<Category> categoryOptional = categoryService.findByName(dto.getName());
        if (categoryOptional.isPresent()) {
            response.addError("Categoria já cadastrada.");
            return ResponseEntity.badRequest().body(response);
        }

        Category category = new Category(dto);
        try {
            categoryService.save(category);
        } catch (Exception e) {
            response.addError("Houve um erro ao persistir os seus dados.");
            return ResponseEntity.badRequest().body(response);
        }

        dto = new CategoryDTO(category);
        response.setData(dto);

        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody CategoryDTO dto, BindingResult result) {
        Response<CategoryDTO> response = new Response<>();

        if (result.hasErrors()) {
            response.setErrors(result);
            return ResponseEntity.badRequest().body(response);
        }

        Optional<Category> categoryOptional = categoryService.findById(id);
        if (!categoryOptional.isPresent()) {
            response.addError("Categoria não encontrada.");
            return ResponseEntity.badRequest().body(response);
        }

        Category category = categoryOptional.get();

        if (!category.getName().equals(dto.getName())) {
            if (categoryService.findByName(dto.getName()).isPresent()) {
                response.addError("Nome sendo usado em outra categoria");
                return ResponseEntity.badRequest().body(response);
            }
        }

        category.update(dto);
        category = categoryService.save(category);

        dto = new CategoryDTO(category);
        response.setData(dto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        log.info("Removendo categoria com id {}", id);

        Response<String> response = new Response<>();

        Optional<Category> category = categoryService.findById(id);

        if (!occurrenceService.findByCategory(id).isEmpty()) {
            response.addError("Não é possível apagar categoria com ocorrências associadas");
            return ResponseEntity.badRequest().body(response);
        }

        if (!category.isPresent()) {
            response.addError("Erro ao apagar categoria com o id " + id);
            return ResponseEntity.badRequest().body(response);
        }

        try {
            this.problemService.deleteAllByCategoryId(id);
            this.categoryService.deleteById(id);
        } catch (ObjectDeletedException e) {
            response.addError("Erro ao apagar categoria");
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }
}
