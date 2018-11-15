package br.edu.utfpr.reclamaguarapuava.model.dto;

import br.edu.utfpr.reclamaguarapuava.model.Category;
import br.edu.utfpr.reclamaguarapuava.model.Problem;
import br.edu.utfpr.reclamaguarapuava.service.CategoryService;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Optional;

public class ProblemDTO {

    @Autowired
    CategoryService categoryService;

    private Long id;

    @NotEmpty(message = "O nome não pode ser vazio")
    @Length(min = 2, max = 100, message = "O nome do problema deve conter no mínimo 2 e máximo 100 caracteres.")
    private String name;

    private Date date_created;

    private String description;

    private Category category;

    public ProblemDTO() {
    }

    public ProblemDTO(Long id, String name, String description, Category category, Date date_created) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.date_created = date_created;
    }

    public ProblemDTO(Problem problem) {
        this.id = problem.getId();
        this.name = problem.getName();
        this.description = problem.getDescription();
        this.category = problem.getCategory();
        this.date_created = problem.getDate_created();
    }

    public ProblemDTO(Optional<Problem> problem) {
        this.id = problem.get().getId();
        this.name = problem.get().getName();
        this.description = problem.get().getDescription();
        this.category = problem.get().getCategory();
        this.date_created = problem.get().getDate_created();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
