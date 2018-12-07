package br.edu.utfpr.reclamaguarapuava.model.repository;

import static org.junit.Assert.assertNotEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.edu.utfpr.reclamaguarapuava.model.Neighborhood;

/**
 *
 * @author Patrick Ribeiro
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class NeighborhoodRepositoryTest {

	@Autowired
	NeighborhoodRepository neighborhoodRepository;

	public NeighborhoodRepositoryTest() {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	@Test
	public void findAll() {
		List<Neighborhood> list = neighborhoodRepository.findAll();
		assertNotEquals(list.size(), 0);
	}

}
