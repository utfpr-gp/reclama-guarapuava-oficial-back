package br.edu.utfpr.reclamaguarapuava.occurrences.endpoint;

import br.edu.utfpr.reclamaguarapuava.occurrences.dtos.NewOccurrenceDTO;
import br.edu.utfpr.reclamaguarapuava.occurrences.service.OccurrenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/occurrences")
public class OccurrenceEndpoint {
    private final OccurrenceService service;

    @Autowired
    public OccurrenceEndpoint(OccurrenceService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OccurrenceService.ResponseNewOccurrence> addNewOccurrence(@Valid @RequestBody NewOccurrenceDTO newOccurrenceDTO) {
        return new ResponseEntity<>(service.newOccurrence(newOccurrenceDTO), HttpStatus.CREATED);
    }
}
