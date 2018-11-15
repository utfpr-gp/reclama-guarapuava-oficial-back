package br.edu.utfpr.reclamaguarapuava.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Occurrence implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum OccurrenceStatus {
        SOLVED, UNRESOLVED, URGENT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OccurrenceStatus status;

    @Column(columnDefinition = "BIGINT")
    private Long view_count;

    @Column(columnDefinition = "BIGINT")
    private Long liked_count;

    @Column(columnDefinition = "BIGINT")
    private Long not_liked_count;

    @ManyToOne
    private Address address;

    @ManyToOne
    private Problem problem;

    @ManyToOne
    private User user;

    @OneToOne
    private Photo photo;

}
