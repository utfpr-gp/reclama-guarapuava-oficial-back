package br.edu.utfpr.reclamaguarapuava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.reclamaguarapuava.model.repository.OccurrenceRepository;

/**
*
* @author agsgoudinho
*/
@Service
public class OccurrenceService {
	@Autowired
	OccurrenceRepository occurrenceRepository;
}
