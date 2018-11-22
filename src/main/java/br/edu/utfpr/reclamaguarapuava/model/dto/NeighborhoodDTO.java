package br.edu.utfpr.reclamaguarapuava.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NeighborhoodDTO {
    @NotBlank
    @Size(min = 4, max = 50)
    private String name;
    private Long cityId;
}
