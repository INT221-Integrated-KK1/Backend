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
    private int statusId;
    @Basic
    @Column(name = "statusName")
    private String statusName;
    @Basic
    @Column(name = "statusDescription")
    private String statusDescription;

}