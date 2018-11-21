package br.edu.utfpr.reclamaguarapuava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.reclamaguarapuava.model.repository.UserRepository;

@Service
public class UserService {
	@Autowired	
	UserRepository userRepository;
}
