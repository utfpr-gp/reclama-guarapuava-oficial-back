package br.edu.utfpr.reclamaguarapuava.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.reclamaguarapuava.model.Occurrence;
import br.edu.utfpr.reclamaguarapuava.model.repository.OccurrenceRepository;

@Service
public class OccurrenceService {

    public static final Logger log = LoggerFactory.getLogger(OccurrenceService.class);

    @Autowired
    OccurrenceRepository occurrenceRepository;

    public List<Occurrence> findByCategory(Long id) {
        return occurrenceRepository.findAllByProblem_CategoryId(id);
    }

    public List<Occurrence> findByProblem(Long id) {
        return occurrenceRepository.findAllByProblemId(id);
    }
}
