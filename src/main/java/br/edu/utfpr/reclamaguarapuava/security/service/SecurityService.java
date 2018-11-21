package br.edu.utfpr.reclamaguarapuava.security.service;

import br.edu.utfpr.reclamaguarapuava.security.configuration.JWTUtil;
import br.edu.utfpr.reclamaguarapuava.security.entities.UserDetailsImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    private final JWTUtil jwtUtil;

    @Autowired
    public SecurityService(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public static UserDetailsImp authenticated() {
        try {
            return (UserDetailsImp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String refreshToken() {
        return jwtUtil.generateToke(authenticated().getUsername());
    }
}
