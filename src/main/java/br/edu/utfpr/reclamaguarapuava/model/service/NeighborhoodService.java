package br.edu.utfpr.reclamaguarapuava.model.service;

import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.utfpr.reclamaguarapuava.model.Neighborhood;
import br.edu.utfpr.reclamaguarapuava.model.dto.NeighborhoodDTO;
import br.edu.utfpr.reclamaguarapuava.model.repository.NeighborhoodRepository;
import br.edu.utfpr.reclamaguarapuava.util.Response;

@Service
public class NeighborhoodService {
	private final NeighborhoodRepository neighborhoodRepository;

	private static final Logger log = LoggerFactory.getLogger(NeighborhoodService.class);

	@Autowired
	public NeighborhoodService(NeighborhoodRepository neighborhoodRepository) {
		this.neighborhoodRepository = neighborhoodRepository;
	}

	@Transactional(readOnly = true)
	public Page<Neighborhood> findAll(Pageable pageable) {
		log.debug("executing query find all");
		return neighborhoodRepository.findAll(pageable);
	}

	@Transactional
	public Response<Neighborhood> findById(Long id) {
		log.debug("executing query find by id: " + id);
		Neighborhood neighborhood = neighborhoodRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("neighborhood not found by id: " + id));
		return new Response<>(neighborhood);
	}

	@Transactional
	public Optional<Neighborhood> findByName(String name) {
		return neighborhoodRepository.findByName(name);
	}

	@Transactional
	public Response<Neighborhood> save(NeighborhoodDTO neighborhoodDTO) {
		log.debug("executing statement insert");
		this.findByName(neighborhoodDTO.getName()).ifPresent(n -> {
			throw new EntityExistsException("neighborhood with this name: " + n.getName()
					+ " exist in database whit id: " + n.getId() + " created: " + n.getCreatedAt());
		});
		return new Response<>(neighborhoodRepository.save(new Neighborhood(neighborhoodDTO)));
	}

	@Transactional
	public Response<Neighborhood> update(NeighborhoodDTO dto, Long id) {
		log.debug("executing statement alter");
		Neighborhood neighborhood = neighborhoodRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("neighborhood not found by id: " + dto.getId()));
		neighborhood.setName(dto.getName());
		return new Response<>(neighborhoodRepository.save(neighborhood));
	}

	public void deleteById(Long id) {
		neighborhoodRepository.deleteById(id);
	}
}
