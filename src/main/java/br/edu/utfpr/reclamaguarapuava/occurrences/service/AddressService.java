package br.edu.utfpr.reclamaguarapuava.occurrences.service;

import br.edu.utfpr.reclamaguarapuava.occurrences.entities.Address;
import br.edu.utfpr.reclamaguarapuava.occurrences.entities.City;
import br.edu.utfpr.reclamaguarapuava.occurrences.entities.Neighborhood;
import br.edu.utfpr.reclamaguarapuava.occurrences.dtos.AddressDTO;
import br.edu.utfpr.reclamaguarapuava.occurrences.dtos.NeighborhoodDTO;
import br.edu.utfpr.reclamaguarapuava.occurrences.repositories.AddressRepository;
import br.edu.utfpr.reclamaguarapuava.occurrences.repositories.CityRepository;
import br.edu.utfpr.reclamaguarapuava.occurrences.repositories.NeighborhoodRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class AddressService {
    private final CityRepository cityRepository;
    private final AddressRepository addressRepository;
    private final NeighborhoodRepository neighborhoodRepository;

    public AddressService(CityRepository cityRepository, AddressRepository addressRepository, NeighborhoodRepository neighborhoodRepository) {
        this.cityRepository = cityRepository;
        this.addressRepository = addressRepository;
        this.neighborhoodRepository = neighborhoodRepository;
    }

    @Transactional
    public Address addNewAddress(AddressDTO addressDTO) {
        NeighborhoodDTO neighborhoodDTO = addressDTO.getNeighborhood();
        Long cityId = neighborhoodDTO.getCityId();
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
