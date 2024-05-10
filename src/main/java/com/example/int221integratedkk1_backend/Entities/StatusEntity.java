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
    @Column(name = "statusName",nullable = false,length = 50)
    private String name;
    @Basic
    @Column(name = "statusDescription",length = 200)
    private String description;


    public void setName(String name) {
        this.name = name != null ? name.trim() : null;
    }

    public void setDescription(String description) {
        this.description = description != null ? description.trim() : null;
    }
}
