package br.edu.utfpr.reclamaguarapuava.model;

import br.edu.utfpr.reclamaguarapuava.util.EntityApplication;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.edu.utfpr.reclamaguarapuava.model.dto.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author Carlos Henrique
 */
@Data
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category extends EntityApplication {

    @Column(length = 45, nullable = false)
    private String name;

    public Category(CategoryDTO categoryDTO) {
        this.id = categoryDTO.getId();
        this.name = categoryDTO.getName();
    }

    public void update(CategoryDTO categoryDTO) {
        this.id = categoryDTO.getId();
        this.name = categoryDTO.getName();
    }
}
