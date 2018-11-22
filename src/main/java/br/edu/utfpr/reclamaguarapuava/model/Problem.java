package br.edu.utfpr.reclamaguarapuava.model;

import br.edu.utfpr.reclamaguarapuava.util.EntityApplication;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
@Table(name = "problem")
public class Problem extends EntityApplication {

    private String name;

    @Column(name = "date_created", nullable = false)
    private Date dateCreated;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    private Category category;

}
