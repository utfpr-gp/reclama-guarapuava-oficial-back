package br.edu.utfpr.reclamaguarapuava.controller;

import br.edu.utfpr.reclamaguarapuava.model.Category;
import br.edu.utfpr.reclamaguarapuava.model.dto.CategoryDTO;
import br.edu.utfpr.reclamaguarapuava.model.service.CategoryService;
import br.edu.utfpr.reclamaguarapuava.util.Response;
import br.edu.utfpr.reclamaguarapuava.util.erros.InvalidParamsException;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/admin/categorias")
@Api(value = "Category", description = "Url para manipular ou buscar categorias")
public class CategoryController {

    private final CategoryService categoryService;

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @ApiOperation(value = "Retorna todas as categorias", notes = "A lista retornada é paginada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Quando bem sucedida para todas as caregorias"),
            @ApiResponse(code = 403, message = "Acesso negado"),
            @ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"),
    })
    public ResponseEntity<Page<Category>> getAll(Pageable pageable) {
        log.debug("Request GET to '/api/v1/admin/categorias' in process");
       return new ResponseEntity<>(categoryService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Retorna uma única categoria")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Quando bem sucedida retorna os detalhes de uma categoria", response = Category.class),
            @ApiResponse(code = 403, message = "Acesso negado"),
            @ApiResponse(code = 404, message = "Categoria não existe na base de dados"),
            @ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"),
    })
    public ResponseEntity<Response<Category>> getById(@ApiParam(name = "id", value = "Númeral do tipo long que corresponde ao id da categoria a qual requer detalhes")
                                                @PathVariable("id") Long id) {
        log.debug("Request GET to '/api/v1/admin/categorias/" + id + "' in process");
        return new ResponseEntity<>(categoryService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Cadastra uma categoria")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Quando bem sucedida cadastra uma nova categoria"),
            @ApiResponse(code = 403, message = "Acesso negado"),
            @ApiResponse(code = 400, message = "Houve um erro, a requisição está inválida"),
            @ApiResponse(code = 422, message = "Houve um erro, a requisição está inválida, existe uma entidade com o mesmo nome"),
            @ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"),
    })
    public ResponseEntity<Response<Category>> save(@Valid @RequestBody CategoryDTO dto, BindingResult result) {
        log.debug("Request POST to '/api/v1/admin/categorias' in process");
        if (result.hasFieldErrors()) {
            throw new InvalidParamsException("Params invalid", result);
        }
        return new ResponseEntity<>(categoryService.save(dto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Atualiza um categoria")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Quando bem sucedida atualiza a categoria"),
            @ApiResponse(code = 403, message = "Acesso negado"),
            @ApiResponse(code = 400, message = "Houve um erro, a requisição está inválida"),
            @ApiResponse(code = 422, message = "Houve um erro de conflito com outra entidade, por exemplo, nome da categoria já existe"),
            @ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"),
    })
    public ResponseEntity<Response<Category>> update(@PathVariable Long id, @Valid @RequestBody CategoryDTO dto, BindingResult result) {
        log.debug("Request PUT to '/api/v1/admin/categorias/" + id + "' in process");
        if (result.hasFieldErrors()) {
            throw new InvalidParamsException("Params to update invalid", result);
        }
        return new ResponseEntity<>(categoryService.update(dto, id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Deleta uma categoria")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Quando bem sucedida deleta a categoria"),
            @ApiResponse(code = 403, message = "Acesso negado"),
            @ApiResponse(code = 410, message = "Houve um erro, o recurso não existe ou já foi elimidado de forma permanente"),
            @ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"),
    })
    public ResponseEntity<?> delete(@PathVariable Long id) {
        log.debug("Request DELETE to '/api/v1/admin/categorias/" + id + "' in process");
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
