package br.edu.utfpr.reclamaguarapuava.model.service;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import br.edu.utfpr.reclamaguarapuava.model.LikedNoliked;
import br.edu.utfpr.reclamaguarapuava.model.User;
import br.edu.utfpr.reclamaguarapuava.model.dto.LikedNoLikedDTO;
import br.edu.utfpr.reclamaguarapuava.model.repository.LikedNoLikedRepository;
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

import javax.persistence.EntityNotFoundException;

import static br.edu.utfpr.reclamaguarapuava.model.Occurrence.OccurrenceStatus.*;

@Service
public class OccurrenceService {
    private final OccurrenceRepository repository;
    private final UserService usersService;
    private final AddressService addressService;
    private final LikedNoLikedRepository likedNoLikedRepository;

    @Autowired
    public OccurrenceService(OccurrenceRepository repository, UserService usersService, AddressService addressService, LikedNoLikedRepository likedNoLikedRepository) {
        this.repository = repository;
        this.usersService = usersService;
        this.addressService = addressService;
        this.likedNoLikedRepository = likedNoLikedRepository;
    }

    @Transactional
    public ResponseOccurrence newOccurrence(NewOccurrenceDTO newOccurrenceDTO) {
        Address address = addressService.addNewAddress(newOccurrenceDTO.getAddress());
        Occurrence occurrence = new Occurrence();
        occurrence.setAddress(address);
        occurrence.setStatus(newOccurrenceDTO.getStatus());
        occurrence.setUser(usersService.findById(newOccurrenceDTO.getUserId()));

        return new ResponseOccurrence(repository.save(occurrence));
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

    @Transactional
    public ResponseOccurrence addFeedbackUser(LikedNoLikedDTO likedNoLikedDTO) {
        Occurrence occurrence = repository.findById(likedNoLikedDTO.getOccurrenceId())
                .orElseThrow(() -> new EntityNotFoundException("Occurrence not found: id" + likedNoLikedDTO.getOccurrenceId()));

        Long currentUserId = SecurityService.authenticated().getId();
        User currentUser = usersService.findById(currentUserId);

        LikedNoliked likedNoliked = new LikedNoliked();
        likedNoliked.setUser(currentUser);
        likedNoliked.setOccurrence(occurrence);
        likedNoliked.setOp(LikedNoliked.Op.valueOf(likedNoLikedDTO.getOp().toLowerCase()));
        likedNoliked = likedNoLikedRepository.save(likedNoliked);

        occurrence.getLikedNolikedList().add(likedNoliked);
        return new ResponseOccurrence(occurrence);
    }

    @Transactional
    public Page<Occurrence> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional
    public Occurrence findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("occurrence not found by id: " + id));
    }

    @Getter
    public class ResponseOccurrence {
        private final Occurrence occurrence;
        private final URI uriOfOccurrence;

        ResponseOccurrence(Occurrence occurrence) {
            this.occurrence = occurrence;
            this.uriOfOccurrence = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                    .buildAndExpand(occurrence.getId()).toUri();
        }
    }
}
