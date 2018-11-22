package br.edu.utfpr.reclamaguarapuava.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.reclamaguarapuava.model.repository.StateRepository;

@Service
public class StateService {
	@Autowired
	StateRepository stateRepository;
}
