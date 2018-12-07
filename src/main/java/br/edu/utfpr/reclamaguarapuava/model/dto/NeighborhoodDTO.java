package br.edu.utfpr.reclamaguarapuava.model.dto;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.edu.utfpr.reclamaguarapuava.model.City;
import br.edu.utfpr.reclamaguarapuava.model.Neighborhood;
import br.edu.utfpr.reclamaguarapuava.util.EntityApplication;
import lombok.Data;

@Data
public class NeighborhoodDTO extends EntityApplication {

	private Long id;

	private City city;

	@NotEmpty(message = "O nome não pode ser vazio")
	@Length(min = 2, max = 100, message = "O nome do Bairro deve conter no mínimo 2 e máximo 100 caracteres.")
	private String name;

	public NeighborhoodDTO() {

	}

	public NeighborhoodDTO(Neighborhood neighborhood) {
		this.id = neighborhood.getId();
		this.name = neighborhood.getName();
		this.city = neighborhood.getCity();
	}

}
