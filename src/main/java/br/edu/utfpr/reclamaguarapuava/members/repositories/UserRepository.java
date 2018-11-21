package br.edu.utfpr.reclamaguarapuava.members.repositories;

import br.edu.utfpr.reclamaguarapuava.members.dtos.NewUserDTO;
import br.edu.utfpr.reclamaguarapuava.members.entities.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

}
