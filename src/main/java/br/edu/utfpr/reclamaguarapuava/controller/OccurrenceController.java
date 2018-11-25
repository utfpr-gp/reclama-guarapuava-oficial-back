package br.edu.utfpr.reclamaguarapuava.controller;

import javax.validation.Valid;

import br.edu.utfpr.reclamaguarapuava.model.dto.LikedNoLikedDTO;
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

@RestController
@RequestMapping("/ocorrencias")
@Api(value = "Occurrence Controller")
@CrossOrigin(origins = "*")
public class OccurrenceController {

    private final OccurrenceService service;

    @Autowired
    public OccurrenceController(OccurrenceService service) {
        this.service = service;
    }

    @GetMapping("/bairro/{neighborhoodId}/categoria/{categoryId}")
    @ApiOperation(value = "Filtra uma Ocorrência")
    public ResponseEntity<Page<Occurrence>> getOccurencesByFilterTo(@PathVariable("neighborhoodId") Long neighborhoodId, @PathVariable("categoryId") Long categoryId, Pageable pageable) {
        return new ResponseEntity<>(service.findByNeighborhoodAndCategory(neighborhoodId, categoryId, pageable), HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value = "Retorna todas as Ocorrências")
    public ResponseEntity<Page<Occurrence>> getAllOccurrence(Pageable pageable) {
        return new ResponseEntity<>(service.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retorna uma única Ocorrência")
    public ResponseEntity<Occurrence> getOccurrenceById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/usuario/{id}")
    @ApiOperation(value = "Retorna uma Ocorrência pelo Usuário")
    public ResponseEntity<Page<Occurrence>> getOccurrencesByUser(@PathVariable("id") Long id, Pageable pageable) {
        return new ResponseEntity<>(service.findByUserId(id, pageable), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping
    @ApiOperation(value = "Cadastra uma Ocorrência")
    public ResponseEntity<OccurrenceService.ResponseOccurrence> addNewOccurrence(@Valid @RequestBody NewOccurrenceDTO newOccurrenceDTO) {
        return new ResponseEntity<>(service.newOccurrence(newOccurrenceDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping("/feedback")
    @ApiOperation(value = "Liked/Notliked Usuário")
    public ResponseEntity<OccurrenceService.ResponseOccurrence> addFeedbackUser(@Valid @RequestBody LikedNoLikedDTO likedNoLikedDTo) {
        return new ResponseEntity<>(service.addFeedbackUser(likedNoLikedDTo), HttpStatus.OK);
    }
}
