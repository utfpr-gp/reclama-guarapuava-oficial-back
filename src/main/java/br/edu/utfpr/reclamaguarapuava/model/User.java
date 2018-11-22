package br.edu.utfpr.reclamaguarapuava.model;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.edu.utfpr.reclamaguarapuava.util.EntityApplication;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author Carlos Henrique
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
@EqualsAndHashCode(callSuper = true)
public class User extends EntityApplication {
    @Column(nullable = false)
    private String name;

    @Column(length = 100, unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(length = 25)
    private String genre;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(length = 11, unique = true, nullable = false)
    private String cpf;

    @ManyToOne
    private City city;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "profiles_users_tb")
    private Set<Integer> profiles = new HashSet<>();

    @Column
    private Boolean isAccountNonExpired = Boolean.TRUE;

    @Column
    private Boolean isAccountNonLocked = Boolean.TRUE;

    @Column
    private Boolean isCredentialsNonExpired = Boolean.TRUE;

    @Column
    private Boolean isEnable = Boolean.TRUE;

    public Set<Profile> getProfiles() {
        return profiles.stream().map(Profile::toEnum).collect(Collectors.toSet());
    }

    public void addProfile(Profile profile) {
        this.profiles.add(profile.getCode());
    }

    @PrePersist
    private void addUserProfile() {
        this.addProfile(Profile.USER);
    }
}
