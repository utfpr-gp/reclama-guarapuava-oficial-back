package br.edu.utfpr.reclamaguarapuava.model.dto;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.edu.utfpr.reclamaguarapuava.model.City;
import br.edu.utfpr.reclamaguarapuava.model.Neighborhood;
import lombok.Data;

@Data
public class NeighborhoodDTO {

	private City city;

	private Long id;

	@NotEmpty(message = "O nome não pode ser vazio")
	@Length(min = 2, max = 100, message = "O nome do Bairro deve conter no mínimo 2 e máximo 100 caracteres.")
	private String name;

	public NeighborhoodDTO(Neighborhood neighborhood) {
		this.id = neighborhood.getId();
		this.name = neighborhood.getName();
	}

	public NeighborhoodDTO(Optional<Neighborhood> neighborhood) {
		this.id = neighborhood.get().getId();
		this.name = neighborhood.get().getName();
	}
}
