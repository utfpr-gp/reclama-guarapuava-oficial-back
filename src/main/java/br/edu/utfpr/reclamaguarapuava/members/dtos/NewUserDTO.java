package br.edu.utfpr.reclamaguarapuava.members.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
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
