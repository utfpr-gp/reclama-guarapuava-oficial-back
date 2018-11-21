package br.edu.utfpr.reclamaguarapuava.occurrences.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.edu.utfpr.reclamaguarapuava.members.entities.User;
import br.edu.utfpr.reclamaguarapuava.structures.EntityApplication;
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
@Table(name = "photo_tb")
@EqualsAndHashCode(callSuper = true)
public class Photo extends EntityApplication {

    @Column(nullable = false)
    private String url;

    @ManyToOne
    private User user;
}
