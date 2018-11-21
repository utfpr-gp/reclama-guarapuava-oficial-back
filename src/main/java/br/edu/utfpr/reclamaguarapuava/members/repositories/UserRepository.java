package br.edu.utfpr.reclamaguarapuava.members.repositories;

import br.edu.utfpr.reclamaguarapuava.members.entities.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
