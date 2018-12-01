package br.edu.utfpr.reclamaguarapuava.controller;

import javax.validation.Valid;

import br.edu.utfpr.reclamaguarapuava.util.Response;
import br.edu.utfpr.reclamaguarapuava.util.erros.InvalidParamsException;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
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
@RequestMapping("/api/v1/usuarios")
@Api(value = "User Controller")
public class UserController {

    private final UserService service;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/admin")
    @ApiOperation(value = "Retorna todos os Usuários", notes = "A lista retornada é paginada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Quando bem sucedida para todos os usuários"),
            @ApiResponse(code = 401, message = "Não autorizado"),
            @ApiResponse(code = 403, message = "Acesso negado"),
            @ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"),
    })
    public ResponseEntity<Page<User>> getAll(Pageable pageable) {
        log.debug("Request GET to '/api/v1/usuarios/admin' in process");
        return new ResponseEntity<>(service.findAll(pageable), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/admin")
    @ApiOperation("Cadastra um Usuário (ADMIN)")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Quando bem sucedida o registro de um novo usuário"),
            @ApiResponse(code = 400, message = "Houve um erro, a requisição está inválida"),
            @ApiResponse(code = 401, message = "Não autorizado"),
            @ApiResponse(code = 403, message = "Acesso negado"),
            @ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"),
    })
    public ResponseEntity<Response<User>> addUser(@Valid @RequestBody NewUserDTO newUserDTO, BindingResult result) {
        log.debug("Request POST to '/api/v1/admin/usuarios/admin' in process");
        if (result.hasErrors()) {
            throw new InvalidParamsException("Params invalid", result);
        }
        return new ResponseEntity<>(service.addNewUser(newUserDTO), HttpStatus.CREATED);
    }

    @PostMapping("/novo")
    @ApiOperation("Cadastra um Usuário")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Quando bem sucedida o registro de um novo usuário"),
            @ApiResponse(code = 400, message = "Houve um erro, a requisição está inválida"),
            @ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"),
    })
    public ResponseEntity<Response<User>> newUser(@Valid @RequestBody NewUserDTO newUserDTO, BindingResult result) {
        log.debug("Request POST to '/api/v1/admin/usuarios/novo' in process");
        if (result.hasErrors()) {
            throw new InvalidParamsException("Params invalid", result);
        }
        return new ResponseEntity<>(service.addNewUser(newUserDTO), HttpStatus.CREATED);
    }
}
