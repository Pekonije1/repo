package com.office.entities;

import com.utils.IdGenerator;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "employee")
@Data
public class Employee {
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * Unique Master Citizen Number (every citizen must have)
     */
    @Column(name = "umcn")
    private Long UMCN;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "work_position_id")
    private Long workPositionId;

    @Column(name = "bank_account")
    private String bankAccount;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "disabled")
    private Boolean disabled = false;


    @PrePersist
    public void generateId() {
        if (this.id == null)
            this.id = IdGenerator.getNewId();
    }
}
