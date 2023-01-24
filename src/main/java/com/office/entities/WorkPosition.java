package com.office.entities;

import com.utils.IdGenerator;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "work_position")
@Data
public class WorkPosition {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @PrePersist
    public void generateId() {
        if (this.id == null)
            this.id = IdGenerator.getNewId();
    }

}
