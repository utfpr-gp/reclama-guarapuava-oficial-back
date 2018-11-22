package br.edu.utfpr.reclamaguarapuava.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    public static enum OccurrenceStatus {
        SOLVED, UNRESOLVED, URGENT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @OneToMany(mappedBy = "occurrence", fetch = FetchType.EAGER)
    private List<LikedNoliked> likedNolikedList;

}
