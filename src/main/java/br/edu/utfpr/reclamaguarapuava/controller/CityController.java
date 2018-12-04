package br.edu.utfpr.reclamaguarapuava.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.reclamaguarapuava.model.City;
import br.edu.utfpr.reclamaguarapuava.model.dto.CityDTO;
import br.edu.utfpr.reclamaguarapuava.model.service.CityService;
import br.edu.utfpr.reclamaguarapuava.util.Response;
import br.edu.utfpr.reclamaguarapuava.util.erros.InvalidParamsException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/admin/cidades")
@Api(value = "City", description = "Url para manipular ou buscar cidades")
public class CityController {

	private final CityService cityService;

	private static final Logger log = LoggerFactory.getLogger(CityController.class);

	@Autowired
	public CityController(CityService cityService) {
		this.cityService = cityService;
	}

	@GetMapping
	@ApiOperation(value = "Retorna todas as cidades", notes = "A lista retornada é paginada")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Quando bem sucedida para todas as cidades"),
			@ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"), })
	public ResponseEntity<Page<City>> getAll(Pageable pageable) {
		log.debug("Request GET to '/api/v1/admin/cidades' in process");
		return new ResponseEntity<>(cityService.findAll(pageable), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Retorna uma única cidade")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Quando bem sucedida retorna os detalhes de uma cidade", response = City.class),
			@ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 404, message = "Cidade não existe na base de dados"),
			@ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"), })
	public ResponseEntity<Response<City>> getById(
			@ApiParam(name = "id", value = "Númeral do tipo long que corresponde ao id da cidade a qual requer detalhes") @PathVariable("id") Long id) {
		log.debug("Request GET to '/api/v1/admin/cidades/" + id + "' in process");
		return new ResponseEntity<>(cityService.findById(id), HttpStatus.OK);
	}

	@PostMapping
	@ApiOperation(value = "Cadastra uma cidade")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Quando bem sucedida cadastra uma nova cidade"),
			@ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 400, message = "Houve um erro, a requisição está inválida"),
			@ApiResponse(code = 422, message = "Houve um erro, a requisição está inválida, existe uma entidade com o mesmo nome"),
			@ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"), })
	public ResponseEntity<Response<City>> save(@Valid @RequestBody CityDTO dto, BindingResult result) {
		log.debug("Request POST to '/api/v1/admin/cidades' in process");
		if (result.hasFieldErrors()) {
			throw new InvalidParamsException("Params invalid", result);
		}
		return new ResponseEntity<>(cityService.save(dto), HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}")
	@ApiOperation(value = "Atualiza uma cidade")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Quando bem sucedida atualiza a cidade"),
			@ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 400, message = "Houve um erro, a requisição está inválida"),
			@ApiResponse(code = 422, message = "Houve um erro de conflito com outra entidade, por exemplo, nome da cidade já existe"),
			@ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"), })
	public ResponseEntity<Response<City>> update(@PathVariable Long id, @Valid @RequestBody CityDTO dto,
			BindingResult result) {
		log.debug("Request PUT to '/api/v1/admin/cidades/" + id + "' in process");
		if (result.hasFieldErrors()) {
			throw new InvalidParamsException("Params to update invalid", result);
		}
		return new ResponseEntity<>(cityService.update(dto, id), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	@ApiOperation(value = "Deleta uma cidade")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Quando bem sucedida deleta o cidade"),
			@ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 410, message = "Houve um erro, o recurso não existe ou já foi elimidado de forma permanente"),
			@ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"), })
	public ResponseEntity<?> delete(@PathVariable Long id) {
		log.debug("Request DELETE to '/api/v1/admin/cidades/" + id + "' in process");
		cityService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
