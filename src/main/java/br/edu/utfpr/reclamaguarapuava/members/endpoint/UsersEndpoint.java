package br.edu.utfpr.reclamaguarapuava.members.endpoint;

import br.edu.utfpr.reclamaguarapuava.members.dtos.NewUserDTO;
import br.edu.utfpr.reclamaguarapuava.members.entities.User;
import br.edu.utfpr.reclamaguarapuava.members.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/users")
public class UsersEndpoint {

    private final UsersService service;

    @Autowired
    public UsersEndpoint(UsersService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<User>> getAll(Pageable pageable) {
        return new ResponseEntity<>(service.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> newUser(@Valid @RequestBody NewUserDTO newUserDTO) {
        return new ResponseEntity<>(service.addNewUser(newUserDTO), HttpStatus.CREATED);
    }
}
