package com.example.int221integratedkk1_backend.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "task", schema = "itb-kk")
public class Task {
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "taskId")
    private int taskId;
    @Basic
    @Column(name = "taskTitle")
    private String taskTitle;
    @Basic
    @Column(name = "taskDescription")
    private String taskDescription;
    @Basic
    @Column(name = "taskAssignees")
    private String taskAssignees;
    @Basic
    @Column(name = "taskStatus")
    private Object taskStatus;

    @Basic
    @Column(name = "createdOn")
    private LocalDateTime createdOn;

    @Basic
    @Column(name = "updatedOn")
    private LocalDateTime updatedOn;


}
