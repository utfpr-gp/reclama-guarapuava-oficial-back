package br.edu.utfpr.reclamaguarapuava.security.configuration;

import java.util.Date;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
    @Value("${jwt.security.secret}")
    private String secret;

    @Value("${jwt.security.expiration}")
    private Long expiration;

    private Predicate<Claims> tokenIsValid;

    public JWTUtil() {
        this.tokenIsValid = (c -> {
            String username = c.getSubject();
            Date expiration =  c.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            return username != null && expiration != null && now.before(expiration);
        });
    }

    public String generateToke(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }

    boolean isValid(String token) {
        return getClaims(token).filter(tokenIsValid).isPresent();
    }

    Optional<String> getEmail(String token) {
        return getClaims(token).map(Claims::getSubject);
    }

    private Optional<Claims> getClaims(String token) {
        try {
            Claims body = Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
            return Optional.of(body);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}
