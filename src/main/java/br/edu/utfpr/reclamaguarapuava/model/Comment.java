package br.edu.utfpr.reclamaguarapuava.model;

import br.edu.utfpr.reclamaguarapuava.util.EntityApplication;
import java.util.Date;

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
@Table(name = "comment")
@EqualsAndHashCode(callSuper = true)
public class Comment extends EntityApplication {

    @Column(name = "date_comment_created", nullable = false)
    private Date dateCommentCreated;

    @ManyToOne
    private User user;

    @ManyToOne
    private Occurrence occurrence;

    @Column(columnDefinition = "TEXT")
    private String description;

}
