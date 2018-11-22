package br.edu.utfpr.reclamaguarapuava.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    @NotBlank
    private Integer number;
    @NotBlank
    @Size(min = 4, max = 50)
    private String street;
    private NeighborhoodDTO neighborhood;
}
