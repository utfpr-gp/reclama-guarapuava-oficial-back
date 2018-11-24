package br.edu.utfpr.reclamaguarapuava.model;

import br.edu.utfpr.reclamaguarapuava.util.EntityApplication;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author Carlos Henrique
 */
@Data
@Entity
@Table(name = "photo")
@EqualsAndHashCode(callSuper = true)
public class Photo extends EntityApplication {

    @Column(nullable = false)
    private String url;

    @ManyToOne
    private User user;
}
