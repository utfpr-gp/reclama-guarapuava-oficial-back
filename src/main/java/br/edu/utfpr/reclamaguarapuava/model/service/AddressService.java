package br.edu.utfpr.reclamaguarapuava.model.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.utfpr.reclamaguarapuava.model.Address;
import br.edu.utfpr.reclamaguarapuava.model.City;
import br.edu.utfpr.reclamaguarapuava.model.Neighborhood;
import br.edu.utfpr.reclamaguarapuava.model.dto.AddressDTO;
import br.edu.utfpr.reclamaguarapuava.model.dto.NeighborhoodDTO;
import br.edu.utfpr.reclamaguarapuava.model.repository.AddressRepository;
import br.edu.utfpr.reclamaguarapuava.model.repository.CityRepository;
import br.edu.utfpr.reclamaguarapuava.model.repository.NeighborhoodRepository;

@Service
public class AddressService {

	@Autowired
	CityRepository cityRepository;

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	NeighborhoodRepository neighborhoodRepository;

	@Transactional
	public Address addNewAddress(AddressDTO addressDTO) {

		NeighborhoodDTO neighborhoodDTO = addressDTO.getNeighborhood();
		Long cityId = neighborhoodDTO.getCity().getId();
		City city = cityRepository.findById(cityId).orElseThrow(() -> new EntityNotFoundException("city not found"));

		Neighborhood neighborhood = new Neighborhood();
		neighborhood.setName(neighborhoodDTO.getName());
		neighborhood.setCity(city);

		neighborhood = neighborhoodRepository.save(neighborhood);

		Address address = new Address();
		address.setStreet(addressDTO.getStreet());
		address.setNumber(addressDTO.getNumber());
		address.setNeighborhood(neighborhood);

		return addressRepository.save(address);
	}

	@Transactional(readOnly = true)
	public City findCityById(Long id) {
		return cityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("city not found"));
	}
}
