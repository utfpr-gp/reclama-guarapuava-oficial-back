package br.edu.utfpr.reclamaguarapuava.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LikedNoLikedDTO {
    @NotBlank
    private Long occurrenceId;

    @NotBlank
    private String op;
}
