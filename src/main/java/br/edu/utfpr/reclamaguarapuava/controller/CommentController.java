package br.edu.utfpr.reclamaguarapuava.controller;

import br.edu.utfpr.reclamaguarapuava.model.Comment;
import br.edu.utfpr.reclamaguarapuava.model.dto.NewCommentDTO;
import br.edu.utfpr.reclamaguarapuava.model.service.CommentService;
import br.edu.utfpr.reclamaguarapuava.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comentarios")
@Api(value = "Comment Controller")
public class CommentController {

    private final CommentService service;

    private static final Logger log = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    public CommentController(CommentService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping
    @ApiOperation(value = "Cadastra um comentário")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Quando bem sucedida cadastra um novo comentário")
        ,@ApiResponse(code = 401, message = "Não autorizado")
        ,@ApiResponse(code = 403, message = "Acesso negado")
        ,@ApiResponse(code = 400, message = "Houve um erro, a requisição está inválida")
        ,@ApiResponse(code = 422, message = "Houve um erro, a requisição está inválida, existe uma entidade com o mesmo nome")
        ,@ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"),})
    public ResponseEntity<Response<Comment>> addNewComment(@Valid @RequestBody NewCommentDTO newCommentDTO) {
        log.debug("Request POST to '/api/v1/comentarios' in process");
        return new ResponseEntity<>(service.newComment(newCommentDTO), HttpStatus.CREATED);
    }

    @GetMapping
    @ApiOperation(value = "Retorna todos os comentários")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Quando bem sucedida para todos os comentários")
        ,@ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"),})
    public ResponseEntity<Page<Comment>> getAllComment(Pageable pageable) {
        log.debug("Request GET to '/api/v1/comentarios' in process");
        return new ResponseEntity<>(service.findAll(pageable), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/{id}")
    @ApiOperation(value = "Retorna um único comentário")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Quando bem sucedida para todos os comentários")
        ,@ApiResponse(code = 404, message = "Comentário não existe na base de dados")
        ,@ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"),})
    public ResponseEntity<Comment> getCommentById(@PathVariable("id") Long id) {
        log.debug("Request GET to '/api/v1/comentarios/" + id + "' in process");
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/ocorrencia/{occurrenceId}")
    @ApiOperation(value = "Retorna comentários pela Ocorrência")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Quando bem sucedida para todos os comentárioso")
        ,@ApiResponse(code = 401, message = "Não autorizado")
        ,@ApiResponse(code = 403, message = "Acesso negado")
        ,@ApiResponse(code = 404, message = "Comentário não existe na base de dados")
        ,@ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"),})
    public ResponseEntity<Page<Comment>> getAllCommentByOccurrence(@PathVariable("occurrenceId") Long id, Pageable pageable) {
        log.debug("Request GET to '/api/v1/comentarios/ocorrencia/" + id + "' in process");
        return new ResponseEntity<>(service.findByOccurrence(id, pageable), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/usuario/{userId}")
    @ApiOperation(value = "Retorna comentários pelo Usuário")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Quando bem sucedida para todos os comentárioso")
        ,@ApiResponse(code = 401, message = "Não autorizado")
        ,@ApiResponse(code = 403, message = "Acesso negado")
        ,@ApiResponse(code = 404, message = "Comentário não existe na base de dados")
        ,@ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"),})
    public ResponseEntity<Page<Comment>> getAllCommentByUser(@PathVariable("userId") Long id, Pageable pageable) {
        log.debug("Request GET to '/api/v1/comentarios/usuario/" + id + "' in process");
        return new ResponseEntity<>(service.findByUser(id, pageable), HttpStatus.OK);
    }
}
