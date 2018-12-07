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

import br.edu.utfpr.reclamaguarapuava.model.Neighborhood;
import br.edu.utfpr.reclamaguarapuava.model.dto.NeighborhoodDTO;
import br.edu.utfpr.reclamaguarapuava.model.service.NeighborhoodService;
import br.edu.utfpr.reclamaguarapuava.util.Response;
import br.edu.utfpr.reclamaguarapuava.util.erros.InvalidParamsException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/admin/bairros")
@Api(value = "Neighborhood", description = "Url para manipular ou buscar bairros")
public class NeighborhoodController {

	private static final Logger log = LoggerFactory.getLogger(NeighborhoodController.class);

	private final NeighborhoodService neighborhoodService;

	@Autowired
	public NeighborhoodController(NeighborhoodService neighborhoodService) {
		this.neighborhoodService = neighborhoodService;
	}

	@GetMapping
	@ApiOperation(value = "Retorna todas os bairros", notes = "A lista retornada é paginada")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Quando bem sucedida para todos os bairros"),
			@ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"), })
	public ResponseEntity<Page<NeighborhoodDTO>> getAll(Pageable pageable) {
		log.debug("Request GET to '/api/v1/admin/bairros' in process");
		Page<Neighborhood> neiPage = this.neighborhoodService.findAll(pageable);
		Page<NeighborhoodDTO> neiPageDto = neiPage.map(s -> new NeighborhoodDTO(s));
		return new ResponseEntity<>(neiPageDto, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Retorna um único bairro")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Quando bem sucedida retorna os detalhes de um bairro", response = Neighborhood.class),
			@ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 404, message = "Bairro não existe na base de dados"),
			@ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"), })
	public ResponseEntity<Response<NeighborhoodDTO>> getById(
			@ApiParam(name = "id", value = "Númeral do tipo long que corresponde ao id do bairro a qual requer detalhes") @PathVariable("id") Long id) {
		log.debug("Request GET to '/api/v1/admin/bairros/" + id + "' in process");
		NeighborhoodDTO neighborhoodDTO;
		neighborhoodDTO = new NeighborhoodDTO(neighborhoodService.findById(id));
		return new ResponseEntity<>(new Response<>(neighborhoodDTO), HttpStatus.OK);
	}

	@PostMapping
	@ApiOperation(value = "Cadastra um bairro")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Quando bem sucedida cadastra um novo bairro"),
			@ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 400, message = "Houve um erro, a requisição está inválida"),
			@ApiResponse(code = 422, message = "Houve um erro, a requisição está inválida, existe uma entidade com o mesmo nome"),
			@ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"), })
	public ResponseEntity<Response<NeighborhoodDTO>> save(@Valid @RequestBody NeighborhoodDTO neighborhoodDTO,
			BindingResult result) {
		log.debug("Request POST to '/api/v1/admin/bairros' in process");
		if (result.hasFieldErrors()) {
			throw new InvalidParamsException("Params invalid", result);
		}
		Neighborhood neighborhood = new Neighborhood(neighborhoodDTO);
		neighborhoodDTO = new NeighborhoodDTO(neighborhoodService.save(neighborhood));
		return new ResponseEntity<>(new Response<>(neighborhoodDTO), HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}")
	@ApiOperation(value = "Atualiza um bairro")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Quando bem sucedida atualiza o bairro"),
			@ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 400, message = "Houve um erro, a requisição está inválida"),
			@ApiResponse(code = 422, message = "Houve um erro de conflito com outra entidade, por exemplo, nome do bairro já existe"),
			@ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"), })
	public ResponseEntity<Response<NeighborhoodDTO>> update(@PathVariable Long id,
			@Valid @RequestBody NeighborhoodDTO neighborhoodDTO, BindingResult result) {
		log.debug("Request PUT to '/api/v1/admin/bairros/" + id + "' in process");
		if (result.hasFieldErrors()) {
			throw new InvalidParamsException("Params to update invalid", result);
		}
		Neighborhood neighborhood = new Neighborhood(neighborhoodDTO);
		neighborhoodDTO = new NeighborhoodDTO(neighborhoodService.update(neighborhood, id));
		return new ResponseEntity<>(new Response<>(neighborhoodDTO), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	@ApiOperation(value = "Deleta um bairro")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Quando bem sucedida deleta o bairro"),
			@ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 410, message = "Houve um erro, o recurso não existe ou já foi elimidado de forma permanente"),
			@ApiResponse(code = 500, message = "Quando a requisição causou um error interno no servidor"), })
	public ResponseEntity<?> delete(@PathVariable Long id) {
		log.debug("Request DELETE to '/api/v1/admin/bairros/" + id + "' in process");
		neighborhoodService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
