package br.edu.utfpr.reclamaguarapuava.controller;

import br.edu.utfpr.reclamaguarapuava.model.Category;
import br.edu.utfpr.reclamaguarapuava.model.Problem;
import br.edu.utfpr.reclamaguarapuava.model.dto.ProblemDTO;
import br.edu.utfpr.reclamaguarapuava.model.dto.ProblemDTO;
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
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/problemas")
@CrossOrigin(origins = "*")
public class ProblemResource {

    private static final Logger log = LoggerFactory.getLogger(ProblemResource.class);

    @Autowired
    OccurrenceService occurrenceService;

    @Autowired
    ProblemService problemService;

    @Value("${page.amount}")
    private int paginationAmount;

    @RequestMapping(method = RequestMethod.GET)
    public List<Problem> index() {
        return problemService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
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

    @RequestMapping(value = "/salvar", method = RequestMethod.POST)
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

    @GetMapping(value = "/paginacao")
    public ResponseEntity<Response<Page<ProblemDTO>>> findAllPaginationWithPage(
            @RequestParam(value = "pag", defaultValue = "0") int page,
            @RequestParam(value = "ord", defaultValue = "name") String order,
            @RequestParam(value = "dir", defaultValue = "ASC") String direction) {

        log.info("Buscando problemas ordenados por {}, página {}", order, page);

        Response<Page<ProblemDTO>> response = new Response<>();

        PageRequest pageRequest = PageRequest.of(page, this.paginationAmount, Sort.Direction.valueOf(direction), order);

        Page<Problem> problems = this.problemService.findAll(pageRequest);
        Page<ProblemDTO> categoryDTOs = problems.map(ProblemDTO::new);

        response.setData(categoryDTOs);
        return ResponseEntity.ok(response);
    }
}
