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
import br.edu.utfpr.reclamaguarapuava.model.repository.NeighborhoodRepository;

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
	public Neighborhood findById(Long id) {
		log.debug("executing query find by id: " + id);
		Neighborhood neighborhood = neighborhoodRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("neighborhood not found by id: " + id));
		return neighborhood;
	}

	@Transactional
	public Optional<Neighborhood> findByName(String name) {
		return neighborhoodRepository.findByName(name);
	}

	@Transactional
	public Neighborhood save(Neighborhood neighborhood) {
		log.debug("executing statement insert");
		this.findByName(neighborhood.getName()).ifPresent(n -> {
			throw new EntityExistsException("neighborhood with this name: " + n.getName()
					+ " exist in database whit id: " + n.getId() + " created: " + n.getCreatedAt());
		});
		return neighborhoodRepository.save(neighborhood);
	}

	@Transactional
	public Neighborhood update(Neighborhood neighborhood, Long id) {
		log.debug("executing statement alter");
		Neighborhood n = neighborhoodRepository.findById(id).orElseThrow(
				() -> new EntityNotFoundException("neighborhood not found by id: " + neighborhood.getId()));
		n.setName(neighborhood.getName());
		return neighborhoodRepository.save(neighborhood);
	}

	public void deleteById(Long id) {
		neighborhoodRepository.deleteById(id);
	}
}
