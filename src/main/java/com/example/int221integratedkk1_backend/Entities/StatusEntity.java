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
    @Column(name = "statusName")
    private String name;
    @Basic
    @Column(name = "statusDescription")
    private String description;


    public void setName(String name) {
        if (name != null) {
            this.name = name.trim();
        }
    }
    public void setDescription(String description) {
        if (description != null) {
            this.description = description.trim();
        }
    }


}
