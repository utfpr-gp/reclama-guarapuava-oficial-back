package br.edu.utfpr.reclamaguarapuava.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.reclamaguarapuava.model.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

}
