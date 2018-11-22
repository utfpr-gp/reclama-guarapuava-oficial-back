package br.edu.utfpr.reclamaguarapuava.model;

import br.edu.utfpr.reclamaguarapuava.util.EntityApplication;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Carlos Henrique
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "occurrence")
public class Occurrence extends EntityApplication {

    public static enum OccurrenceStatus {
        SOLVED, UNRESOLVED, URGENT
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OccurrenceStatus status;

    @Column(name = "view_count", columnDefinition = "BIGINT")
    private Long viewCount;

    @ManyToOne
    private Address address;

    @ManyToOne
    private Problem problem;

    @ManyToOne
    private User user;

    @OneToOne
    private Photo photo;

}
