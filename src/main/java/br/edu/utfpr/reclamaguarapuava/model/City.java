package br.edu.utfpr.reclamaguarapuava.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.edu.utfpr.reclamaguarapuava.model.dto.CityDTO;
import br.edu.utfpr.reclamaguarapuava.util.EntityApplication;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Carlos Henrique
 */
@Data
@Entity
@Table(name = "city")
@EqualsAndHashCode(callSuper = true)
public class City extends EntityApplication {

	@ManyToOne
	private State state;

	@Column(length = 50, nullable = false)
	private String name;

	public City() {

	}

	public City(CityDTO cityDTO) {
		this.id = cityDTO.getId();
		this.name = cityDTO.getName();
	}

	public void update(CityDTO cityDTO) {
		this.id = cityDTO.getId();
		this.name = cityDTO.getName();
	}

}
