package br.edu.utfpr.reclamaguarapuava.controller;

import java.util.Optional;

import javax.validation.Valid;

import br.edu.utfpr.reclamaguarapuava.model.dto.NewProblemDTO;
import br.edu.utfpr.reclamaguarapuava.model.service.OccurrenceService;
import br.edu.utfpr.reclamaguarapuava.util.erros.InvalidParamsException;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.hibernate.ObjectDeletedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api/v1/admin/problemas")
@Api(value = "Problem Controller")
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
    @ApiOperation(value = "Retorna todos os Problemas", notes = "A lista retornada é paginada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Quando bem sucedida para todos problemas"),
            @ApiResponse(code = 403, message = "Acesso negado"),
            @ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"),
    })
    public ResponseEntity<Page<Problem>> findAll(Pageable pageable) {
        log.debug("Request GET to '/api/v1/admin/problemas' in process");
        return new ResponseEntity<>(problemService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Retorna um único Problema")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Quando bem sucedida o problemas"),
            @ApiResponse(code = 403, message = "Acesso negado"),
            @ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"),
    })
    public ResponseEntity<Response<Problem>> getById(@PathVariable Long id) {
        log.debug("Request GET to '/api/v1/admin/problemas/'" + id + "' in process");
        return new ResponseEntity<>(problemService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Cadastra um Problema")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Quando bem sucedida registar um novo problema"),
            @ApiResponse(code = 400, message = "Houve um erro, a requisição está inválida"),
            @ApiResponse(code = 403, message = "Acesso negado"),
            @ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"),
    })
    public ResponseEntity<Response<Problem>> save(@Valid @RequestBody NewProblemDTO dto, BindingResult result) {
        log.debug("Request POST to '/api/v1/admin/problemas/' in process");
        if (result.hasErrors()) {
            throw new InvalidParamsException("Params invalid", result);
        }
        return new ResponseEntity<>(problemService.save(dto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Atualiza um Problema")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Quando bem sucedida atualiza o problema"),
            @ApiResponse(code = 400, message = "Houve um erro, a requisição está inválida"),
            @ApiResponse(code = 403, message = "Acesso negado"),
            @ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"),
    })
    public ResponseEntity<Response<Problem>> update(@PathVariable Long id, @Valid @RequestBody ProblemDTO dto, BindingResult result) {
        log.debug("Request PUT to '/api/v1/admin/problemas/'" + id + "' in process");
        if (result.hasErrors()) {
            throw new InvalidParamsException("Params invalid", result);
        }
        return new ResponseEntity<>(problemService.update(dto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Deleta um Problema")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Quando bem sucedida deleta a categoria"),
            @ApiResponse(code = 403, message = "Acesso negado"),
            @ApiResponse(code = 410, message = "Houve um erro, o recurso não existe ou já foi elimidado de forma permanente"),
            @ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"),
    })
    public ResponseEntity<?> delete(@PathVariable Long id) {
        log.debug("Request DELETE to '/api/v1/admin/problemas' in process");
        problemService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
