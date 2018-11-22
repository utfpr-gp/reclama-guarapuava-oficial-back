package br.edu.utfpr.reclamaguarapuava.model.service;


import java.net.URI;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.utfpr.reclamaguarapuava.model.City;
import br.edu.utfpr.reclamaguarapuava.model.User;
import br.edu.utfpr.reclamaguarapuava.model.dto.NewUserDTO;
import br.edu.utfpr.reclamaguarapuava.model.repository.UserRepository;
import lombok.Getter;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddressService addressService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository repository, AddressService addressService) {
        this.userRepository = repository;
        this.addressService = addressService;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public ResponseNewUser addNewUser(NewUserDTO newUserDTO) {
        User user = new User();        
        user.setName(newUserDTO.getName());
        user.setEmail(newUserDTO.getEmail());
        user.setBirthday(newUserDTO.getBirthday());
        user.setPassword(bCryptPasswordEncoder.encode(newUserDTO.getPassword()));
        user.setCpf(newUserDTO.getCpf());

        City city = addressService.findCityById(newUserDTO.getCityId());
        user.setCity(city);        

        return new ResponseNewUser(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("user not found"));
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Getter
    public class ResponseNewUser {
        private final User user;
        private final URI uriOfUser;

        public ResponseNewUser(User user) {
            this.user = user;
            this.uriOfUser = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(user.getId()).toUri();
        }
    }
}
