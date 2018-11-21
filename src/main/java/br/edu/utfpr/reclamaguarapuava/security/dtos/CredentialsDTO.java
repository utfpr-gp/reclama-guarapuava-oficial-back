package br.edu.utfpr.reclamaguarapuava.security.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class CredentialsDTO {
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 20)
    private String password;
}
