package br.edu.utfpr.reclamaguarapuava.model.service;

import br.edu.utfpr.reclamaguarapuava.model.Comment;
import br.edu.utfpr.reclamaguarapuava.model.dto.NewCommentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.reclamaguarapuava.model.repository.CommentRepository;
import br.edu.utfpr.reclamaguarapuava.util.Response;
import java.time.LocalDate;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    private final CommentRepository repository;
    private final UserService userService;
    private final OccurrenceService occurrenceService;

    private static final Logger log = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    public CommentService(CommentRepository repository, UserService userService,
            OccurrenceService occurrenceService) {
        this.repository = repository;
        this.userService = userService;
        this.occurrenceService = occurrenceService;
    }

    @Transactional(readOnly = true)
    public Page<Comment> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional
    public Response<Comment> newComment(NewCommentDTO newCommentDTO) {
        log.debug("executing statement insert");
        Comment comment = new Comment();
        comment.setUser(userService.findById(newCommentDTO.getUserId()).getData());
        comment.setOccurrence(occurrenceService.findById(newCommentDTO.getOccurrenceId()));
        comment.setDateCommentCreated(LocalDate.now());
        comment.setDescription(newCommentDTO.getDescription());

        return new Response<>(repository.save(comment));
    }

    @Transactional
    public Comment findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("comment not found by id: " + id));
    }

    @Transactional(readOnly = true)
    public Page<Comment> findByOccurrence(Long id, Pageable pageable) {
        log.debug("executing query find by occurrence");
        return repository.findAllByOccurrenceId(id, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Comment> findByUser(Long id, Pageable pageable) {
        log.debug("executing query find by user");
        return repository.findAllByUserId(id, pageable);
    }
}
