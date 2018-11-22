package br.edu.utfpr.reclamaguarapuava.util;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class EntityApplication implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column
    protected LocalDate createdAt;

    @Column
    protected LocalDate updateAt;

    @PrePersist
    private void persistedConfigure() {
        this.createdAt = LocalDate.now();
        this.updateAt = LocalDate.now();
    }

    @PreUpdate
    private void updatedConfigure() {
        this.updateAt = LocalDate.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityApplication that = (EntityApplication) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt);
    }
}
