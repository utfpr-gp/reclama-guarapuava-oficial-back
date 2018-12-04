package br.edu.utfpr.reclamaguarapuava.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.reclamaguarapuava.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByOccurrenceId(Long id, Pageable pageable);

    Page<Comment> findAllByUserId(Long id, Pageable pageable);
}
