package br.edu.utfpr.reclamaguarapuava.occurrences.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.edu.utfpr.reclamaguarapuava.members.entities.User;
import br.edu.utfpr.reclamaguarapuava.occurrences.entities.Occurrence;
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
@Table(name = "comment_tb")
@EqualsAndHashCode(callSuper = true)
public class Comment extends EntityApplication {

    @Column(nullable = false)
    private Date date_comment_created;

    @ManyToOne
    private User user;

    @ManyToOne
    private Occurrence occurrence;

    @Column(columnDefinition = "TEXT")
    private String description;

}
