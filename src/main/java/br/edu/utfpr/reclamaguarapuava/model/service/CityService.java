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

import br.edu.utfpr.reclamaguarapuava.model.City;
import br.edu.utfpr.reclamaguarapuava.model.dto.CityDTO;
import br.edu.utfpr.reclamaguarapuava.model.repository.CityRepository;
import br.edu.utfpr.reclamaguarapuava.util.Response;

@Service
public class CityService {

	private final CityRepository cityRepository;

	private static final Logger log = LoggerFactory.getLogger(CityService.class);

	@Autowired
	public CityService(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}

	@Transactional(readOnly = true)
	public Page<City> findAll(Pageable pageable) {
		log.debug("executing query find all");
		return cityRepository.findAll(pageable);
	}

	@Transactional
	public Response<City> findById(Long id) {
		log.debug("executing query find by id: " + id);
		City city = cityRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("city not found by id: " + id));
		return new Response<>(city);
	}

	@Transactional
	public Optional<City> findByName(String name) {
		return cityRepository.findByName(name);
	}

	@Transactional
	public Response<City> save(CityDTO cityDTO) {
		log.debug("executing statement insert");
		this.findByName(cityDTO.getName()).ifPresent(c -> {
			throw new EntityExistsException("city with this name: " + c.getName() + " exist in database whit id: "
					+ c.getId() + " created: " + c.getCreatedAt());
		});
		return new Response<>(cityRepository.save(new City(cityDTO)));
	}

	@Transactional
	public Response<City> update(CityDTO dto, Long id) {
		log.debug("executing statement alter");
		City city = cityRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("city not found by id: " + dto.getId()));
		city.setName(dto.getName());
		return new Response<>(cityRepository.save(city));
	}

	public void deleteById(Long id) {
		cityRepository.deleteById(id);
	}
}
