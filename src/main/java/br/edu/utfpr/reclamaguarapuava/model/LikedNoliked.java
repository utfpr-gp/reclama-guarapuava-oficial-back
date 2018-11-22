package br.edu.utfpr.reclamaguarapuava.model;

import br.edu.utfpr.reclamaguarapuava.util.EntityApplication;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "liked_noliked")
@EqualsAndHashCode(callSuper = true)
public class LikedNoliked extends EntityApplication {

    public enum Op {
        LIKED, NOLIKED
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Op op;

    @JsonBackReference
    @ManyToOne
    private Occurrence occurrence;

    @ManyToOne
    private User user;

}
