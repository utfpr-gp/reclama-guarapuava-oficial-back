package br.edu.utfpr.reclamaguarapuava.controller;

import java.util.Optional;

import javax.validation.Valid;

import br.edu.utfpr.reclamaguarapuava.model.service.OccurrenceService;
import org.hibernate.ObjectDeletedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.reclamaguarapuava.model.Problem;
import br.edu.utfpr.reclamaguarapuava.model.dto.ProblemDTO;
import br.edu.utfpr.reclamaguarapuava.model.service.ProblemService;
import br.edu.utfpr.reclamaguarapuava.util.Response;

@RestController
@RequestMapping("/api/admin/problemas")
public class ProblemController {
    private final OccurrenceService occurrenceService;
    private final ProblemService problemService;

    private static final Logger log = LoggerFactory.getLogger(ProblemController.class);

    @Autowired
    public ProblemController(OccurrenceService occurrenceService, ProblemService problemService) {
        this.occurrenceService = occurrenceService;
        this.problemService = problemService;
    }

    @GetMapping
    public ResponseEntity<Response<Page<ProblemDTO>>> index(Pageable pageable) {
        Response<Page<ProblemDTO>> response = new Response<>();

        Page<Problem> problems = problemService.findAll(pageable);

        Page<ProblemDTO> problemDTOS = problems.map(ProblemDTO::new);

        response.setData(problemDTOS);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<ProblemDTO>> getById(@PathVariable Long id) {
        Response<ProblemDTO> response = new Response<>();

        Optional<Problem> problem = problemService.findById(id);

        if (!problem.isPresent()) {
            response.addError("Problema não encontrado");
            return ResponseEntity.badRequest().body(response);
        }

        ProblemDTO problemDTO = new ProblemDTO(problem);

        response.setData(problemDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<ProblemDTO>> store(@Valid @RequestBody ProblemDTO dto, BindingResult result) {
        Response<ProblemDTO> response = new Response<>();

        if (result.hasErrors()) {
            response.setErrors(result);
            return ResponseEntity.badRequest().body(response);
        }

        Optional<Problem> problemOptional = problemService.findByName(dto.getName());
        if (problemOptional.isPresent() && problemOptional.get().getDescription().equals(dto.getDescription())) {
            response.addError("Problema já cadastrado.");
            return ResponseEntity.badRequest().body(response);
        }

        Problem problem = new Problem(dto);
        try {
            problemService.save(problem);
        } catch (Exception e) {
            response.addError("Houve um erro ao persistir os seus dados.");
            return ResponseEntity.badRequest().body(response);
        }

        dto = new ProblemDTO(problem);
        response.setData(dto);

        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ProblemDTO dto, BindingResult result) {
        Response<ProblemDTO> response = new Response<>();

        if (result.hasErrors()) {
            response.setErrors(result);
            return ResponseEntity.badRequest().body(response);
        }

        Optional<Problem> problemOptional = problemService.findById(id);
        if (!problemOptional.isPresent()) {
            response.addError("Problema não encontrado.");
            return ResponseEntity.badRequest().body(response);
        }

        Problem problem = problemOptional.get();

        if (!problem.getName().equals(dto.getName())) {
            Optional<Problem> problem1 = problemService.findByName(dto.getName());
            if (problem1.isPresent() && problem1.get().getDescription().equals(dto.getDescription())) {
                response.addError("Nome e descrição sendo usados em outro problema");
                return ResponseEntity.badRequest().body(response);
            }
        }

        problem.update(dto);
        problem = problemService.save(problem);

        dto = new ProblemDTO(problem);
        response.setData(dto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        log.info("Removendo problema com id {}", id);

        Response<String> response = new Response<>();

        Optional<Problem> problemOptional = problemService.findById(id);

        if (!occurrenceService.findByProblem(id).isEmpty()) {
            response.addError("Não é possível apagar problema com ocorrências associadas");
            return ResponseEntity.badRequest().body(response);
        }

        if (!problemOptional.isPresent()) {
            response.addError("Erro ao apagar problema com o id " + id);
            return ResponseEntity.badRequest().body(response);
        }

        try {
            this.problemService.deleteById(id);
        } catch (ObjectDeletedException e) {
            response.addError("Erro ao apagar problema");
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }
}
