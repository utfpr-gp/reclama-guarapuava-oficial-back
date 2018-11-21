package br.edu.utfpr.reclamaguarapuava.security.endpoints;

import br.edu.utfpr.reclamaguarapuava.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
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
