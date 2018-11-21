package br.edu.utfpr.reclamaguarapuava.members.endpoint;

import br.edu.utfpr.reclamaguarapuava.members.dtos.NewUserDTO;
import br.edu.utfpr.reclamaguarapuava.members.entities.User;
import br.edu.utfpr.reclamaguarapuava.members.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
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

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UsersService.ResponseNewUser> addUser(@Valid @RequestBody NewUserDTO newUserDTO) {
        return new ResponseEntity<>(service.addNewUser(newUserDTO), HttpStatus.CREATED);
    }

    @PostMapping("/register")
    public ResponseEntity<UsersService.ResponseNewUser> newUser(@Valid @RequestBody NewUserDTO newUserDTO) {
        return new ResponseEntity<>(service.addNewUser(newUserDTO), HttpStatus.CREATED);
    }
}
