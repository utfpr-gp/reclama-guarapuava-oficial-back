package br.edu.utfpr.reclamaguarapuava.model.dto;

import java.util.Date;
import java.util.Optional;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.edu.utfpr.reclamaguarapuava.model.Category;
import br.edu.utfpr.reclamaguarapuava.model.Problem;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProblemDTO {

    private Long id;

    @NotEmpty(message = "O nome não pode ser vazio")
    @Length(min = 2, max = 100, message = "O nome do problema deve conter no mínimo 2 e máximo 100 caracteres.")
    private String name;

    private Date dateCreated;

    private String description;

    private Category category;

    public ProblemDTO(Problem problem) {
        this.id = problem.getId();
        this.name = problem.getName();
        this.description = problem.getDescription();
        this.category = problem.getCategory();
        this.dateCreated = problem.getDateCreated();
    }

    public ProblemDTO(Optional<Problem> problem) {
        this.id = problem.get().getId();
        this.name = problem.get().getName();
        this.description = problem.get().getDescription();
        this.category = problem.get().getCategory();
        this.dateCreated = problem.get().getDateCreated();
    }
}
