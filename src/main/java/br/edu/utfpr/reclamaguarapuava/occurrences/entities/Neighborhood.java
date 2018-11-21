package br.edu.utfpr.reclamaguarapuava.occurrences.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.edu.utfpr.reclamaguarapuava.structures.EntityApplication;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author Carlos Henrique
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "neighborhood_tb")
@EqualsAndHashCode(callSuper = true)
public class Neighborhood extends EntityApplication {
    @ManyToOne
    private City city;

    @Column(nullable = false, length = 50)
    private String name;
}
