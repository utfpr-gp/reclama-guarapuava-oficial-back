package br.edu.utfpr.reclamaguarapuava.security.entities;

import br.edu.utfpr.reclamaguarapuava.members.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserDetailsImp implements UserDetails {
    private Long id;
    private String email;
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    private Boolean isAccountNonExpired;
    private Boolean isAccountNonLocked;
    private Boolean isCredentialsNonExpired;
    private Boolean isEnable;

    public UserDetailsImp() { }

    public UserDetailsImp(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.isAccountNonExpired = user.getIsAccountNonExpired();
        this.isAccountNonLocked = user.getIsAccountNonLocked();
        this.isCredentialsNonExpired = user.getIsCredentialsNonExpired();
        this.isEnable = user.getIsEnable();
        this.authorities = user.getProfiles().stream()
                .map(profile -> new SimpleGrantedAuthority(profile.getDescription()))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public Long getId() {
        return this.id;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnable;
    }
}
