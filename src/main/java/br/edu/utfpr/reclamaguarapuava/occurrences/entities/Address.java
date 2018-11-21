package br.edu.utfpr.reclamaguarapuava.occurrences.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.edu.utfpr.reclamaguarapuava.occurrences.entities.Neighborhood;
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
@Table(name = "address_tb")
@EqualsAndHashCode(callSuper = true)
public class Address extends EntityApplication {

    @Column(length = 10, nullable = false)
    private Integer number;

    @Column(length = 50, nullable = false)
    private String street;

    @ManyToOne
    private Neighborhood neighborhood;

}
