package br.edu.utfpr.reclamaguarapuava.service;

import br.edu.utfpr.reclamaguarapuava.model.Category;
import br.edu.utfpr.reclamaguarapuava.model.Problem;
import br.edu.utfpr.reclamaguarapuava.model.repository.CategoryRepository;
import br.edu.utfpr.reclamaguarapuava.model.repository.ProblemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProblemService {

    private static final Logger log = LoggerFactory.getLogger(ProblemService.class);

    @Autowired
    ProblemRepository problemRepository;

    public void deleteAllByCategoryId(Long id) {
        this.problemRepository.deleteAllByCategoryId(id);
    }
}
