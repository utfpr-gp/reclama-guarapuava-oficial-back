package br.edu.utfpr.reclamaguarapuava.service;

import br.edu.utfpr.reclamaguarapuava.model.Category;
import br.edu.utfpr.reclamaguarapuava.model.Occurrence;
import br.edu.utfpr.reclamaguarapuava.model.repository.CategoryRepository;
import br.edu.utfpr.reclamaguarapuava.model.repository.OccurrenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OccurrenceService {

    public static final Logger log = LoggerFactory.getLogger(OccurrenceService.class);

    @Autowired
    OccurrenceRepository occurrenceRepository;

    public List<Occurrence> findByCategory(Long id) {
        return occurrenceRepository.findByCategory(id);
    }
}
