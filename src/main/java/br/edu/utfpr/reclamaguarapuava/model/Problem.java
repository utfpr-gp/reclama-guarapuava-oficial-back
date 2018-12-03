package br.edu.utfpr.reclamaguarapuava.model;

import br.edu.utfpr.reclamaguarapuava.util.EntityApplication;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.edu.utfpr.reclamaguarapuava.model.dto.ProblemDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Carlos Henrique
 */
@Data
@Entity
@Table(name = "problem")
@EqualsAndHashCode(callSuper = true)
public class Problem extends EntityApplication {
    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @ManyToOne
    private Category category;
}
