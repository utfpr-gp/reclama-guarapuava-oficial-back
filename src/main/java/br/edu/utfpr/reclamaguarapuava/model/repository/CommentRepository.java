package br.edu.utfpr.reclamaguarapuava.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.reclamaguarapuava.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
