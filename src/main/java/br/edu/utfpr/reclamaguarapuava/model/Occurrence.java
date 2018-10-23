package br.edu.utfpr.reclamaguarapuava.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "occurrence")
public class Occurrence implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum OccurrenceStatus {
        SOLUCIONADO, NAO_SOLUCIONADO, NAO_SOLUCIONADO_URGENTE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Category category;

    private String address;

    @ManyToOne
    private Neighborhood neighborhood;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private Byte[] photo;

    @Enumerated(EnumType.STRING)
    private OccurrenceStatus status;

    private Long views;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "occurrence", targetEntity = Comment.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "occurrence", targetEntity = Comment.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<LikedNotLiked> liked_notliked;

}
