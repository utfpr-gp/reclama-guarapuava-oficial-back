package br.edu.utfpr.reclamaguarapuava.model.service;

import br.edu.utfpr.reclamaguarapuava.model.Category;
import br.edu.utfpr.reclamaguarapuava.model.Problem;
import br.edu.utfpr.reclamaguarapuava.model.dto.NewProblemDTO;
import br.edu.utfpr.reclamaguarapuava.model.dto.ProblemDTO;
import br.edu.utfpr.reclamaguarapuava.model.repository.CategoryRepository;
import br.edu.utfpr.reclamaguarapuava.model.repository.ProblemRepository;
import br.edu.utfpr.reclamaguarapuava.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class ProblemService {
    private final ProblemRepository problemRepository;
    private final CategoryRepository categoryRepository;

    private static final Logger log = LoggerFactory.getLogger(ProblemService.class);

    @Autowired
    public ProblemService(ProblemRepository problemRepository, CategoryRepository categoryRepository) {
        this.problemRepository = problemRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<Problem> findAll(Pageable pageable) {
        log.debug("executing query find all");
        return problemRepository.findAll(pageable);
    }

    @Transactional
    public Response<Problem> findById(Long id) {
        log.debug("executing query find by id");
        Problem problem = this.problemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("problem not found by id: " + id));
        return new Response<>(problem);
    }

    @Transactional
    public Response<Problem> save(NewProblemDTO dto) {
        log.debug("executing statement insert");
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("category not found by id: " + dto.getCategoryId()));
        Problem problem = new Problem();
        problem.setName(dto.getName());
        problem.setCategory(category);
        problem.setDescription(dto.getDescription());
        return new Response<>(problemRepository.save(problem));
    }

    @Transactional
    public void deleteById(Long id) {
        log.debug("executing statement delete");
        this.problemRepository.deleteById(id);
    }

    @Transactional
    public Response<Problem> findByName(String name) {
        log.debug("executing query find by name");
        Problem problem = problemRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("problem not found by name: " + name));
        return new Response<>(problem);
    }

    @Transactional
    public Response<Problem> update(ProblemDTO dto) {
        log.debug("executing statement alter");
        Problem problem = problemRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("problem not found by id: " + dto.getId()));

        if (!problem.getCategory().getId().equals(dto.getCategoryId())) {
            Category category = categoryRepository.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("category not found by id: " + dto.getCategoryId()));
            problem.setCategory(category);
        }

        problem.setName(dto.getName());
        problem.setDescription(dto.getDescription());

        return new Response<>(problemRepository.save(problem));
    }
}
