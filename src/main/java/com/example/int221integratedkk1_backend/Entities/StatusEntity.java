package com.example.int221integratedkk1_backend.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Entity
@Getter
@Setter

@Table(name = "Status", schema = "ITB-KK-V2")
public class StatusEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "statusId")
    private int id;
    @Basic
    @Column(name = "statusName", length = 50, nullable = false)
    private String name;
    @Basic
    @Column(name = "statusDescription", length = 200)
    private String description;


    public void setName(String name) {
        if (name != null) {

            String trimmedName = name.trim();

            if (trimmedName.isEmpty()) {
                this.name = null;
            } else {
                this.name = trimmedName;
            }
        }
    }

    public void setDescription(String description) {
        if (description != null) {

            String trimmedDescription = description.trim();

            if (trimmedDescription.isEmpty()) {
                this.description = null;
            } else {
                this.description = trimmedDescription;
            }
        }
    }




}
