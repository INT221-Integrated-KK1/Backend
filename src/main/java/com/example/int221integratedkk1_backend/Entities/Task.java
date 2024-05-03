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
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "taskId")
    private int taskId;

    @Column(name = "taskTitle", nullable = false)
    private String taskTitle;

    @Column(name = "taskDescription")
    private String taskDescription;

    @Column(name = "taskAssignees")
    private String taskAssignees;


    @Column(name = "taskStatus", nullable = false,columnDefinition = "varchar(255) default 'No Status'")
    private Object taskStatus;

    @Column(name = "createdOn")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'",timezone = "UTC")
    private ZonedDateTime createdOn;

    @Column(name = "updatedOn")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'",timezone = "UTC")
    private ZonedDateTime updatedOn;


}
