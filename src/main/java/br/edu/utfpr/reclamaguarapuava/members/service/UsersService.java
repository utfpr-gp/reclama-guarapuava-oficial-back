package br.edu.utfpr.reclamaguarapuava.members.service;

import br.edu.utfpr.reclamaguarapuava.members.dtos.NewUserDTO;
import br.edu.utfpr.reclamaguarapuava.members.entities.User;
import br.edu.utfpr.reclamaguarapuava.members.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private final UserRepository repository;

    public UsersService(UserRepository repository) {
        this.repository = repository;
    }

    public User addNewUser(NewUserDTO newUserDTO) {
        return repository.save(newUserDTO);
    }

    public Page<User> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
