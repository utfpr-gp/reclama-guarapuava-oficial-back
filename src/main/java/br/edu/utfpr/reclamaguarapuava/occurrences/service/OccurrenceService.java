package br.edu.utfpr.reclamaguarapuava.occurrences.service;

import br.edu.utfpr.reclamaguarapuava.members.entities.Profile;
import br.edu.utfpr.reclamaguarapuava.members.service.UsersService;
import br.edu.utfpr.reclamaguarapuava.occurrences.entities.Address;
import br.edu.utfpr.reclamaguarapuava.occurrences.dtos.NewOccurrenceDTO;
import br.edu.utfpr.reclamaguarapuava.occurrences.entities.Occurrence;
import br.edu.utfpr.reclamaguarapuava.occurrences.repositories.OccurrenceRepository;
import br.edu.utfpr.reclamaguarapuava.security.entities.UserDetailsImp;
import br.edu.utfpr.reclamaguarapuava.security.service.SecurityService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
public class OccurrenceService {
    private final OccurrenceRepository repository;
    private final UsersService usersService;
    private final AddressService addressService;

    @Autowired
    public OccurrenceService(OccurrenceRepository repository, UsersService usersService, AddressService addressService) {
        this.repository = repository;
        this.usersService = usersService;
        this.addressService = addressService;
    }

    @Transactional
    public ResponseNewOccurrence newOccurrence(NewOccurrenceDTO newOccurrenceDTO) {
        Address address = addressService.addNewAddress(newOccurrenceDTO.getAddress());
        Occurrence occurrence = new Occurrence();
        occurrence.setAddress(address);
        occurrence.setStatus(newOccurrenceDTO.getStatus());
        occurrence.setUser(usersService.findById(newOccurrenceDTO.getUserId()));

        return new ResponseNewOccurrence(repository.save(occurrence));
    }

    public Page<Occurrence> findByUserId(Long id, Pageable pageable) throws AuthenticationException {
        UserDetailsImp userDetailsImp = SecurityService.authenticated();

        if (userDetailsImp.hasAdmin() || id.equals(userDetailsImp.getId())) {
            return repository.findAllByUserId(id, pageable);
        }

        throw new AuthenticationServiceException("access denied");
    }

    @Getter
    public class ResponseNewOccurrence {
        private final Occurrence occurrence;
        private final URI uriOfOccurrence;

        public ResponseNewOccurrence(Occurrence occurrence) {
            this.occurrence = occurrence;
            this.uriOfOccurrence = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(occurrence.getId()).toUri();
        }
    }
}
