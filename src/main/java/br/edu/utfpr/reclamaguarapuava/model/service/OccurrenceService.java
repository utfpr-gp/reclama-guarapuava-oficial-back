package br.edu.utfpr.reclamaguarapuava.model.service;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.utfpr.reclamaguarapuava.model.Address;
import br.edu.utfpr.reclamaguarapuava.model.Occurrence;
import br.edu.utfpr.reclamaguarapuava.model.dto.NewOccurrenceDTO;
import br.edu.utfpr.reclamaguarapuava.model.repository.OccurrenceRepository;
import br.edu.utfpr.reclamaguarapuava.security.entities.UserDetailsImp;
import br.edu.utfpr.reclamaguarapuava.security.service.SecurityService;
import lombok.Getter;

import static br.edu.utfpr.reclamaguarapuava.model.Occurrence.OccurrenceStatus.*;

@Service
public class OccurrenceService {
    private final OccurrenceRepository repository;
    private final UserService usersService;
    private final AddressService addressService;

    @Autowired
    public OccurrenceService(OccurrenceRepository repository, UserService usersService, AddressService addressService) {
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

    @Transactional(readOnly = true)
    public Page<Occurrence> findByNeighborhoodAndCategory(Long neighborhoodId, Long categoryId, Pageable pageable) {
        List<Occurrence.OccurrenceStatus> filter = Arrays.asList(URGENT, UNRESOLVED);
        return repository.findAllByAddress_NeighborhoodIdAndProblem_CategoryIdAndStatusIn(neighborhoodId, categoryId, filter, pageable);
    }

    @Getter
    public class ResponseNewOccurrence {
        private final Occurrence occurrence;
        private final URI uriOfOccurrence;

        public ResponseNewOccurrence(Occurrence occurrence) {
            this.occurrence = occurrence;
            this.uriOfOccurrence = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                    .buildAndExpand(occurrence.getId()).toUri();
        }
    }

    public List<Occurrence> findByCategory(Long id) {
        return repository.findAllByProblem_CategoryId(id);
    }

    public List<Occurrence> findByProblem(Long id) {
        return repository.findAllByProblemId(id);
    }
}
