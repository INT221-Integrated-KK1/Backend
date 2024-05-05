package com.example.int221integratedkk1_backend.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "task", schema = "itb-kk")
public class Task {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "taskId")
    private int id;

    @Column(name = "taskTitle", nullable = false)
    private String title;

    @Column(name = "taskDescription")
    private String description;

    @Column(name = "taskAssignees")
    private String assignees;

    @Column(name = "taskStatus", nullable = false,columnDefinition = "varchar(255) default 'No Status'")
    private Object status;

    @Column(name = "createdOn", updatable = false,insertable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'",timezone = "UTC")
    private ZonedDateTime createdOn;

    @Column(name = "updatedOn",insertable = false,updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'",timezone = "UTC")
    private ZonedDateTime updatedOn;



}
