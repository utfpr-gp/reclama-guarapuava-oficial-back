package br.edu.utfpr.reclamaguarapuava.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.edu.utfpr.reclamaguarapuava.model.dto.NeighborhoodDTO;
import br.edu.utfpr.reclamaguarapuava.util.EntityApplication;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Carlos Henrique
 */
@Data
@Entity
@Table(name = "neighborhood")
@EqualsAndHashCode(callSuper = true)
public class Neighborhood extends EntityApplication {

	@ManyToOne
	private City city;

	@Column(nullable = false, length = 50)
	private String name;

	public Neighborhood() {

	}

	public Neighborhood(NeighborhoodDTO neighborhoodDTO) {
		this.id = neighborhoodDTO.getId();
		this.name = neighborhoodDTO.getName();
	}

	public void update(NeighborhoodDTO neighborhoodDTO) {
		this.id = neighborhoodDTO.getId();
		this.name = neighborhoodDTO.getName();
	}
}
