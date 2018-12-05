package br.edu.utfpr.reclamaguarapuava.controller;

import javax.validation.Valid;

import br.edu.utfpr.reclamaguarapuava.model.dto.LikedNoLikedDTO;
import br.edu.utfpr.reclamaguarapuava.util.Response;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.edu.utfpr.reclamaguarapuava.model.Occurrence;
import br.edu.utfpr.reclamaguarapuava.model.dto.NewOccurrenceDTO;
import br.edu.utfpr.reclamaguarapuava.model.service.OccurrenceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ocorrencias")
@Api(value = "Occurrence Controller")
public class OccurrenceController {

    private final OccurrenceService service;

    private static final Logger log = LoggerFactory.getLogger(OccurrenceController.class);

    @Autowired
    public OccurrenceController(OccurrenceService service) {
        this.service = service;
    }

    @GetMapping("/bairro/{neighborhoodId}/categoria/{categoryId}")
    @ApiOperation(value = "Filtra uma Ocorrência")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Quando bem sucedida para todas as ocorrências filtradas por bairro e categorias")
        ,@ApiResponse(code = 404, message = "Bairro ou categoria não existe na base de dados")
        ,@ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"),})
    public ResponseEntity<Page<Occurrence>> getOccurencesByFilterTo(@PathVariable("neighborhoodId") Long neighborhoodId,
            @PathVariable("categoryId") Long categoryId,
            Pageable pageable) {
        log.debug("Request GET to '/bairro/" + neighborhoodId + "/categoria/" + categoryId + "' in process");
        return new ResponseEntity<>(service.findByNeighborhoodAndCategory(neighborhoodId, categoryId, pageable), HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value = "Retorna todas as Ocorrências")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Quando bem sucedida para todas as ocorrencias")
        ,@ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"),})
    public ResponseEntity<Page<Occurrence>> getAllOccurrence(Pageable pageable) {
        log.debug("Request GET to '/api/v1/ocorrencias' in process");
        return new ResponseEntity<>(service.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retorna uma única Ocorrência")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Quando bem sucedida para todas as ocorrências")
        ,@ApiResponse(code = 404, message = "Ocorrencia não existe na base de dados")
        ,@ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"),})
    public ResponseEntity<Occurrence> getOccurrenceById(@PathVariable("id") Long id) {
        log.debug("Request GET to '/api/v1/ocorrencias/" + id + "' in process");
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/usuario/{id}")
    @ApiOperation(value = "Retorna uma Ocorrência pelo Usuário")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Quando bem sucedida para todas as ocorrências do usuário")
        ,@ApiResponse(code = 401, message = "Não autorizado")
        ,@ApiResponse(code = 403, message = "Acesso negado")
        ,@ApiResponse(code = 404, message = "Usuário não existe na base de dados")
        ,@ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"),})
    public ResponseEntity<Page<Occurrence>> getOccurrencesByUser(@PathVariable("id") Long id, Pageable pageable) {
        log.debug("Request GET to '/api/v1/ocorrencias/usuario/" + id + "' in process");
        return new ResponseEntity<>(service.findByUserId(id, pageable), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping
    @ApiOperation(value = "Cadastra uma Ocorrência")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Quando bem sucedida cadastra uma nova ocorrência")
        ,@ApiResponse(code = 401, message = "Não autorizado")
        ,@ApiResponse(code = 403, message = "Acesso negado")
        ,@ApiResponse(code = 400, message = "Houve um erro, a requisição está inválida")
        ,@ApiResponse(code = 422, message = "Houve um erro, a requisição está inválida, existe uma entidade com o mesmo nome")
        ,@ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"),})
    public ResponseEntity<Response<Occurrence>> addNewOccurrence(@Valid @RequestBody NewOccurrenceDTO newOccurrenceDTO) {
        log.debug("Request POST to '/api/v1/ocorrencias' in process");
        return new ResponseEntity<>(service.newOccurrence(newOccurrenceDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping("/feedback")
    @ApiOperation(value = "Liked/Notliked Usuário")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Quando bem sucedida cadastra um novo feedback para ocorrência")
        ,@ApiResponse(code = 401, message = "Não autorizado")
        ,@ApiResponse(code = 403, message = "Acesso negado")
        ,@ApiResponse(code = 400, message = "Houve um erro, a requisição está inválida")
        ,@ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"),})
    public ResponseEntity<Response<Occurrence>> addFeedbackUser(@Valid @RequestBody LikedNoLikedDTO likedNoLikedDTo) {
        log.debug("Request PUT to '/api/v1/ocorrencias/feedback' in process");
        return new ResponseEntity<>(service.addFeedbackUser(likedNoLikedDTo), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/bairro/{neighborhoodId}")
    @ApiOperation(value = "Filtra ocorrências por bairro")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Quando bem sucedida para todas as ocorrências filtradas por bairro")
        ,@ApiResponse(code = 401, message = "Não autorizado")
        ,@ApiResponse(code = 403, message = "Acesso negado")
        ,@ApiResponse(code = 404, message = "Bairro não existe na base de dados")
        ,@ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor")
    })
    public ResponseEntity<Page<Occurrence>> getOccurrencesByFilterToNeighborhood(@PathVariable("neighborhoodId") Long neighborhoodId, Pageable pageable) {
        log.debug("Request GET to '/bairro/" + neighborhoodId + "' in process");
        return new ResponseEntity<>(service.findByNeighborhoodId(neighborhoodId, pageable), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/categoria/{categoryId}")
    @ApiOperation(value = "Filtra ocorrências por categoria")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Quando bem sucedida para todas as ocorrências filtradas por categoria")
        ,@ApiResponse(code = 401, message = "Não autorizado")
        ,@ApiResponse(code = 403, message = "Acesso negado")
        ,@ApiResponse(code = 404, message = "Categoria não existe na base de dados")
        ,@ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor")
    })
    public ResponseEntity<Page<Occurrence>> getOccurrencesByFilterToCategory(@PathVariable("categoryId") Long categoryId, Pageable pageable) {
        log.debug("Request GET to '/categoria/" + categoryId + "' in process");
        return new ResponseEntity<>(service.findByCategory(categoryId, pageable), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/status/{status}")
    @ApiOperation(value = "Filtra ocorrências por status")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Quando bem sucedida para todas as ocorrências filtradas por status")
        ,@ApiResponse(code = 401, message = "Não autorizado")
        ,@ApiResponse(code = 403, message = "Acesso negado")
        ,@ApiResponse(code = 404, message = "Status não existe na base de dados")
        ,@ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor")
    })
    public ResponseEntity<Page<Occurrence>> getOccurrencesByFilterToStatus(@PathVariable("status") List<Occurrence.OccurrenceStatus> status, Pageable pageable) {
        log.debug("Request GET to '/status/" + status + "' in process");
        return new ResponseEntity<>(service.findAllByStatusIn(status, pageable), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/data-inicio/{startDate}/data-fim/{endDate}")
    @ApiOperation(value = "Filtra ocorrências por data")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Quando bem sucedida para todas as ocorrências filtradas por data")
        ,@ApiResponse(code = 401, message = "Não autorizado")
        ,@ApiResponse(code = 403, message = "Acesso negado")
        ,@ApiResponse(code = 404, message = "Ocorrência não existe na base de dados")
        ,@ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor")
    })
    public ResponseEntity<Page<Occurrence>> getOccurrencesByCreatedAtBetween(@PathVariable("startDate") String startDate,
            @PathVariable("endDate") String endDate, Pageable pageable) {
        log.debug("Request GET to '/data-inicio/" + startDate + "/data-fim/" + endDate + "' in process");
        return new ResponseEntity<>(service.findAllByCreatedAtBetween(LocalDate.parse(startDate),
                LocalDate.parse(endDate), pageable), HttpStatus.OK);
    }
}
