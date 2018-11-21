package br.edu.utfpr.reclamaguarapuava.occurrences.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class NeighborhoodDTO {
    @NotBlank
    @Size(min = 4, max = 50)
    private String name;
    private Long cityId;
}
