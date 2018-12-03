package br.edu.utfpr.reclamaguarapuava.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.reclamaguarapuava.model.Occurrence;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OccurrenceRepository extends JpaRepository<Occurrence, Long> {

    Page<Occurrence> findAllByUserId(Long id, Pageable pageable);

    Page<Occurrence> findAllByProblem_CategoryId(Long id, Pageable pageable);

    List<Occurrence> findAllByProblem_CategoryId(Long id);

    Page<Occurrence> findAllByProblemId(Long id, Pageable pageable);

    List<Occurrence> findAllByProblemId(Long id);

    Page<Occurrence> findAllByAddress_NeighborhoodIdAndProblem_CategoryIdAndStatusIn(
            Long neighborhoodId, Long categoryId, List<Occurrence.OccurrenceStatus> statuses, Pageable pageable
    );

    Page<Occurrence> findAllByStatusIn(List<Occurrence.OccurrenceStatus> statuses, Pageable pageable);

    Page<Occurrence> findAllByAddress_NeighborhoodId(Long id, Pageable pageable);

}
