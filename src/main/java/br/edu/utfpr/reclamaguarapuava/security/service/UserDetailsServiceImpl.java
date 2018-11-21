package br.edu.utfpr.reclamaguarapuava.security.service;

import br.edu.utfpr.reclamaguarapuava.members.entities.User;
import br.edu.utfpr.reclamaguarapuava.members.service.UsersService;
import br.edu.utfpr.reclamaguarapuava.security.entities.UserDetailsImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UsersService usersService;

    @Autowired
    public UserDetailsServiceImpl(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = usersService.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("username not found: " + email));
        return new UserDetailsImp(user);
    }
}
