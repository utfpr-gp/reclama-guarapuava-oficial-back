package br.edu.utfpr.reclamaguarapuava.security.endpoints;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.reclamaguarapuava.security.service.SecurityService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthEndpoint {
    private final SecurityService securityService;

    @Autowired
    public AuthEndpoint(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<Void> refresToken(HttpServletResponse response) {
        String token = securityService.refreshToken();
        response.addHeader("Authorization", "Bearer " + token);
        return ResponseEntity.noContent().build();
    }
}
