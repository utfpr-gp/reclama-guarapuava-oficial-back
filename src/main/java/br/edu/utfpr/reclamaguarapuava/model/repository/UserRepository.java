package br.edu.utfpr.reclamaguarapuava.model.repository;

import java.util.Optional;

import br.edu.utfpr.reclamaguarapuava.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
