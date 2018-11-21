package br.edu.utfpr.reclamaguarapuava.members.service;

import br.edu.utfpr.reclamaguarapuava.members.dtos.NewUserDTO;
import br.edu.utfpr.reclamaguarapuava.members.entities.User;
import br.edu.utfpr.reclamaguarapuava.members.repositories.UserRepository;
import br.edu.utfpr.reclamaguarapuava.occurrences.entities.City;
import br.edu.utfpr.reclamaguarapuava.occurrences.service.AddressService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;

@Service
public class UsersService {

    private final UserRepository userRepository;
    private final AddressService addressService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UsersService(UserRepository repository, AddressService addressService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = repository;
        this.addressService = addressService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
