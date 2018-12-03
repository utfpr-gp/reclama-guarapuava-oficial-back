package br.edu.utfpr.reclamaguarapuava.model.service;


import br.edu.utfpr.reclamaguarapuava.model.City;
import br.edu.utfpr.reclamaguarapuava.model.User;
import br.edu.utfpr.reclamaguarapuava.model.dto.NewUserDTO;
import br.edu.utfpr.reclamaguarapuava.model.repository.UserRepository;
import br.edu.utfpr.reclamaguarapuava.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddressService addressService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository repository, AddressService addressService) {
        this.userRepository = repository;
        this.addressService = addressService;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public Response<User> addNewUser(NewUserDTO newUserDTO) {
        log.debug("executing statement insert");
        User user = new User();        
        user.setName(newUserDTO.getName());
        user.setEmail(newUserDTO.getEmail());
        user.setBirthday(newUserDTO.getBirthday());
        user.setPassword(bCryptPasswordEncoder.encode(newUserDTO.getPassword()));
        user.setCpf(newUserDTO.getCpf());

        City city = addressService.findCityById(newUserDTO.getCityId());
        user.setCity(city);        

        return new Response<>(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        log.debug("executing query find all");
        return userRepository.findAll(pageable);
    }

    @Transactional
    public Response<User> findById(Long userId) {
        log.debug("executing query find by id");
        return new Response<>(userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("user not found")));
    }

    @Transactional
    public Optional<User> findByEmail(String email) {
        log.debug("executing query find by email");
        return userRepository.findByEmail(email);
    }
}
