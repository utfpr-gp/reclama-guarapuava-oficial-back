package br.edu.utfpr.reclamaguarapuava.model.repository;

import br.edu.utfpr.reclamaguarapuava.model.Occurrence;
import br.edu.utfpr.reclamaguarapuava.model.Occurrence.OccurrenceStatus;
import static br.edu.utfpr.reclamaguarapuava.model.Occurrence.OccurrenceStatus.UNRESOLVED;
import static br.edu.utfpr.reclamaguarapuava.model.Occurrence.OccurrenceStatus.URGENT;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Carlos Henrique
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class OccurrenceRepositoryTest {

    @Autowired
    OccurrenceRepository occurrenceRepository;

    public OccurrenceRepositoryTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void findAllByUserId() {
        Page<Occurrence> page = occurrenceRepository.findAllByUserId(1L, new PageRequest(1, 20));
        assertNotEquals(page.getTotalElements(), 0);
    }

    @Test
    public void findAllByProblem_CategoryId() {
        List<Occurrence> occurrenceList = occurrenceRepository.findAllByProblem_CategoryId(1L);
        assertFalse(occurrenceList.isEmpty());
    }

    @Test
    public void findAllByProblemId() {
        List<Occurrence> occurrenceList = occurrenceRepository.findAllByProblemId(1L);
        assertFalse(occurrenceList.isEmpty());
    }

    @Test
    public void findAllByAddress_NeighborhoodIdAndProblem_CategoryIdAndStatusIn() {
        List<OccurrenceStatus> filter = Arrays.asList(URGENT, UNRESOLVED);
        Page<Occurrence> page = occurrenceRepository.findAllByAddress_NeighborhoodIdAndProblem_CategoryIdAndStatusIn(1L, 1L, filter, new PageRequest(1, 20));
        assertNotEquals(page.getTotalElements(), 0);
    }

    @Test
    public void findAllByStatusIn() {
        List<OccurrenceStatus> status = new ArrayList<>();
        status.add(OccurrenceStatus.UNRESOLVED);
        Page<Occurrence> page = occurrenceRepository.findAllByStatusIn(status, new PageRequest(1, 20));
        assertNotEquals(page.getTotalElements(), 0);
    }

}
