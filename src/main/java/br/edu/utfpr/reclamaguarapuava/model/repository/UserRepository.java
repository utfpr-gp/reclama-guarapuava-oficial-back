package br.edu.utfpr.reclamaguarapuava.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.reclamaguarapuava.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
