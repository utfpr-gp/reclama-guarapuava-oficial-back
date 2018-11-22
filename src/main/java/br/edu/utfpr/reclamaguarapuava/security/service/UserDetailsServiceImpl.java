package br.edu.utfpr.reclamaguarapuava.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.utfpr.reclamaguarapuava.model.User;
import br.edu.utfpr.reclamaguarapuava.model.service.UserService;
import br.edu.utfpr.reclamaguarapuava.security.entities.UserDetailsImp;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService usersService;

    @Autowired
    public UserDetailsServiceImpl(UserService usersService) {
        this.usersService = usersService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = usersService.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("username not found: " + email));
        return new UserDetailsImp(user);
    }
}
