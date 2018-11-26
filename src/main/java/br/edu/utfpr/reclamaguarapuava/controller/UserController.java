package br.edu.utfpr.reclamaguarapuava.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.reclamaguarapuava.model.User;
import br.edu.utfpr.reclamaguarapuava.model.dto.NewUserDTO;
import br.edu.utfpr.reclamaguarapuava.model.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/usuarios")
@Api(value = "User Controller")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    @ApiOperation(value = "Retorna todos os Usuários")
    public ResponseEntity<Page<User>> getAll(Pageable pageable) {
        return new ResponseEntity<>(service.findAll(pageable), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    @ApiOperation("Cadastra um Usuário (ADMIN)")
    public ResponseEntity<UserService.ResponseNewUser> addUser(@Valid @RequestBody NewUserDTO newUserDTO) {
        return new ResponseEntity<>(service.addNewUser(newUserDTO), HttpStatus.CREATED);
    }

    @PostMapping("/novo")
    @ApiOperation("Cadastra um Usuário")
    public ResponseEntity<UserService.ResponseNewUser> newUser(@Valid @RequestBody NewUserDTO newUserDTO) {
        return new ResponseEntity<>(service.addNewUser(newUserDTO), HttpStatus.CREATED);
    }
}
