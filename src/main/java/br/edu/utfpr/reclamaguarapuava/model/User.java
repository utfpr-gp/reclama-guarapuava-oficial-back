package br.edu.utfpr.reclamaguarapuava.model;

import br.edu.utfpr.reclamaguarapuava.security.ProfileEnum;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author Carlos Henrique
 */
@Data
@Entity
@Table(name = "user_tb")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 100, unique = true, nullable = false)
    private String email;

    @Column(nullable = false, length = 20)
    private String password;

    @Column(length = 25)
    private String genre;

    @Column(nullable = false)
    private Date date_birth;

    @Column(length = 11, unique = true, nullable = false)
    private String cpf;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProfileEnum profile;

    @OneToOne
    private City city;

    private Date created;
    private Date updated;
}
