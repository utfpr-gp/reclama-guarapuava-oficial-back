package br.edu.utfpr.reclamaguarapuava.model;

import br.edu.utfpr.reclamaguarapuava.util.EntityApplication;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

/**
 *
 * @author Carlos Henrique
 */
@Data
@Entity
@Table(name = "state")
public class State extends EntityApplication {

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 5)
    private String abbvr;
}
