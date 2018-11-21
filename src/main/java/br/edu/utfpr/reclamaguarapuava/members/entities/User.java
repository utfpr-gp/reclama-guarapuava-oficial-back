package br.edu.utfpr.reclamaguarapuava.members.entities;

import br.edu.utfpr.reclamaguarapuava.occurrences.entities.City;
import br.edu.utfpr.reclamaguarapuava.structures.EntityApplication;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 *
 * @author Carlos Henrique
 */
@Data
@Entity
@Table(name = "user_tb")
@EqualsAndHashCode(callSuper = true)
public class User extends EntityApplication {
    @Column(nullable = false)
    private String name;

    @Column(length = 100, unique = true, nullable = false)
    private String email;

    @Column(nullable = false, length = 20)
    private String password;

    @Column(length = 25)
    private String genre;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(length = 11, unique = true, nullable = false)
    private String cpf;

    @ManyToOne
    private City city;
}
