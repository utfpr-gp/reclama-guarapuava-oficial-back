package br.edu.utfpr.reclamaguarapuava.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Carlos Henrique
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 100, unique = true, nullable = false, columnDefinition = "VARCHAR(100)")
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 25, columnDefinition = "VARCHAR(25)")
    private String genre;

    @Column(nullable = false, columnDefinition = "DATE")
    private Date date_birth;

    @Column(length = 11, unique = true, nullable = false, columnDefinition = "VARCHAR(11)")
    private String cpf;

    @OneToOne
    private City city;

    @OneToMany(cascade = CascadeType.REMOVE)
    private Set<Occurrence> occurences;

    @OneToMany(cascade = CascadeType.REMOVE)
    private Set<Comment> comments;
}
