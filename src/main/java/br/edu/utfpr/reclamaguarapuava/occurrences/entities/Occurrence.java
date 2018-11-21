package br.edu.utfpr.reclamaguarapuava.occurrences.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.edu.utfpr.reclamaguarapuava.members.entities.User;
import br.edu.utfpr.reclamaguarapuava.model.Problem;
import br.edu.utfpr.reclamaguarapuava.structures.EntityApplication;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Carlos Henrique
 */
@Data
@Entity
@Table(name = "occurrence_tb")
@EqualsAndHashCode(callSuper = true)
public class Occurrence extends EntityApplication {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OccurrenceStatus status;

    @Column(columnDefinition = "BIGINT")
    private Long viewCount;

    @Column(columnDefinition = "BIGINT")
    private Long likedCount;

    @Column(columnDefinition = "BIGINT")
    private Long notLikedCount;

    @ManyToOne
    private Address address;

    @ManyToOne
    private Problem problem;

    @ManyToOne
    private User user;

    @OneToOne
    private Photo photo;

    public enum OccurrenceStatus {
        SOLVED, UNRESOLVED, URGENT
    }
}
