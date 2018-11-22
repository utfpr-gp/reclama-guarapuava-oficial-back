package br.edu.utfpr.reclamaguarapuava.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.edu.utfpr.reclamaguarapuava.model.dto.ProblemDTO;
import lombok.Data;

/**
 *
 * @author Carlos Henrique
 */
@Data
@Entity
@Table(name = "problem")
public class Problem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "date_created", nullable = false)
    private Date dateCreated;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    private Category category;

    public Problem() {

    }

    public Problem(String name, Date date_created, String description, Category category) {
        this.name = name;
        this.dateCreated = date_created;
        this.description = description;
        this.category = category;
    }

    public Problem(ProblemDTO problemDTO) {
        this.id = problemDTO.getId();
        this.name = problemDTO.getName();
        this.description = problemDTO.getDescription();
        this.dateCreated = problemDTO.getDateCreated();
        this.category = problemDTO.getCategory();
    }

    public void update(ProblemDTO problemDTO) {
        this.id = problemDTO.getId();
        this.name = problemDTO.getName();
        this.description = problemDTO.getDescription();
        this.dateCreated = problemDTO.getDateCreated();
        this.category = problemDTO.getCategory();
    }
}
