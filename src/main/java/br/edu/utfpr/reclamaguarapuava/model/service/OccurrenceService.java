package br.edu.utfpr.reclamaguarapuava.model.service;

import br.edu.utfpr.reclamaguarapuava.model.Address;
import br.edu.utfpr.reclamaguarapuava.model.LikedNoliked;
import br.edu.utfpr.reclamaguarapuava.model.Occurrence;
import br.edu.utfpr.reclamaguarapuava.model.User;
import br.edu.utfpr.reclamaguarapuava.model.dto.LikedNoLikedDTO;
import br.edu.utfpr.reclamaguarapuava.model.dto.NewOccurrenceDTO;
import br.edu.utfpr.reclamaguarapuava.model.repository.LikedNolikedRepository;
import br.edu.utfpr.reclamaguarapuava.model.repository.OccurrenceRepository;
import br.edu.utfpr.reclamaguarapuava.security.entities.UserDetailsImp;
import br.edu.utfpr.reclamaguarapuava.security.service.SecurityService;
import br.edu.utfpr.reclamaguarapuava.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

import static br.edu.utfpr.reclamaguarapuava.model.Occurrence.OccurrenceStatus.UNRESOLVED;
import static br.edu.utfpr.reclamaguarapuava.model.Occurrence.OccurrenceStatus.URGENT;

@Service
public class OccurrenceService {

    private final OccurrenceRepository repository;
    private final UserService usersService;
    private final AddressService addressService;
    private final LikedNolikedRepository likedNoLikedRepository;

    private static final Logger log = LoggerFactory.getLogger(OccurrenceService.class);

    @Autowired
    public OccurrenceService(OccurrenceRepository repository, UserService usersService, AddressService addressService, LikedNolikedRepository likedNoLikedRepository) {
        this.repository = repository;
        this.usersService = usersService;
        this.addressService = addressService;
        this.likedNoLikedRepository = likedNoLikedRepository;
    }

    @Transactional
    public Response<Occurrence> newOccurrence(NewOccurrenceDTO newOccurrenceDTO) {
        log.debug("executing statement insert");
        Address address = addressService.addNewAddress(newOccurrenceDTO.getAddress());
        Occurrence occurrence = new Occurrence();
        occurrence.setAddress(address);
        occurrence.setStatus(newOccurrenceDTO.getStatus());
        occurrence.setUser(usersService.findById(newOccurrenceDTO.getUserId()).getData());

        return new Response<>(repository.save(occurrence));
    }

    @Transactional
    public Page<Occurrence> findByUserId(Long id, Pageable pageable) throws AuthenticationException {
        log.debug("executing query find by id");
        UserDetailsImp userDetailsImp = SecurityService.authenticated();
        if (userDetailsImp.hasAdmin() || id.equals(userDetailsImp.getId())) {
            return repository.findAllByUserId(id, pageable);
        }
        throw new AuthenticationServiceException("access denied");
    }

    @Transactional(readOnly = true)
    public Page<Occurrence> findByNeighborhoodAndCategory(Long neighborhoodId, Long categoryId, Pageable pageable) {
        log.debug("executing query find all");
        List<Occurrence.OccurrenceStatus> filter = Arrays.asList(URGENT, UNRESOLVED);
        return repository.findAllByAddress_NeighborhoodIdAndProblem_CategoryIdAndStatusIn(neighborhoodId, categoryId, filter, pageable);
    }

    @Transactional
    public Response<Occurrence> addFeedbackUser(LikedNoLikedDTO likedNoLikedDTO) {
        log.debug("executing statement insert LikedNoLiked");
        Occurrence occurrence = repository.findById(likedNoLikedDTO.getOccurrenceId())
                .orElseThrow(() -> new EntityNotFoundException("Occurrence not found: id" + likedNoLikedDTO.getOccurrenceId()));

        Long currentUserId = SecurityService.authenticated().getId();
        User currentUser = usersService.findById(currentUserId).getData();

        LikedNoliked likedNoliked = new LikedNoliked();
        likedNoliked.setUser(currentUser);
        likedNoliked.setOccurrence(occurrence);
        likedNoliked.setOp(LikedNoliked.Op.valueOf(likedNoLikedDTO.getOp().toLowerCase()));
        likedNoliked = likedNoLikedRepository.save(likedNoliked);

        occurrence.getLikedNolikedList().add(likedNoliked);
        return new Response<>(occurrence);
    }

    @Transactional(readOnly = true)
    public Page<Occurrence> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional
    public Occurrence findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("occurrence not found by id: " + id));
    }

    @Transactional(readOnly = true)
    public Page<Occurrence> findByCategory(Long id, Pageable pageable) {
        log.debug("executing query find by category");
        return repository.findAllByProblem_CategoryId(id, pageable);
    }

    @Transactional
    public List<Occurrence> findByCategory(Long id) {
        log.debug("executing query find by category");
        return repository.findAllByProblem_CategoryId(id);
    }

    @Transactional(readOnly = true)
    public Page<Occurrence> findByProblem(Long id, Pageable pageable) {
        log.debug("executing query find by problem");
        return repository.findAllByProblemId(id, pageable);
    }

    @Transactional
    public List<Occurrence> findByProblem(Long id) {
        log.debug("executing query find by problem");
        return repository.findAllByProblemId(id);
    }

    @Transactional(readOnly = true)
    public Page<Occurrence> findByNeighborhoodId(Long id, Pageable pageable) {
        log.debug("executing query find by neighborhood");
        return repository.findAllByAddress_NeighborhoodId(id, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Occurrence> findAllByStatusIn(List<Occurrence.OccurrenceStatus> status, Pageable pageable) {
        log.debug("executing query find by status");
        return repository.findAllByStatusIn(status, pageable);
    }

}
