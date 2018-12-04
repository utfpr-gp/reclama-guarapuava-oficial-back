package br.edu.utfpr.reclamaguarapuava.model.dto;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.edu.utfpr.reclamaguarapuava.model.City;
import lombok.Data;

@Data
public class CityDTO {

	private Long id;

	@NotEmpty(message = "O nome não pode ser vazio")
	@Length(min = 2, max = 100, message = "O nome da Cidade deve conter no mínimo 2 e máximo 100 caracteres.")
	private String name;

	public CityDTO(City city) {
		this.id = city.getId();
		this.name = city.getName();
	}

	public CityDTO(Optional<City> city) {
		this.id = city.get().getId();
		this.name = city.get().getName();
	}
}
