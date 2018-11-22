package br.edu.utfpr.reclamaguarapuava.model.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUserDTO {
    @NotBlank
    @Size(min = 4, max = 255)
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 8, max = 20)
    private String password;
//    @CPF
    private String cpf;

    @NotBlank
    private String genre;

    @NotBlank
    private LocalDate birthday;

    @NotBlank
    private Long cityId;
}
