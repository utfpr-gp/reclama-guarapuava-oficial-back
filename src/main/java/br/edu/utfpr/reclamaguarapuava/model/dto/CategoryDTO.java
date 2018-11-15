package br.edu.utfpr.reclamaguarapuava.model.dto;

import br.edu.utfpr.reclamaguarapuava.model.Category;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;

public class CategoryDTO {

    private Long id;

    @NotEmpty(message = "O nome não pode ser vazio")
    @Length(min = 2, max = 100, message = "O nome da categoria deve conter no mínimo 2 e máximo 100 caracteres.")
    private String name;

    public CategoryDTO() {
    }

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }

    public CategoryDTO(Optional<Category> category) {
        this.id = category.get().getId();
        this.name = category.get().getName();
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
}
