package com.example.int221integratedkk1_backend.DTOS;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class TaskDTO {

    private int taskId;

    private String taskTitle;

    private String taskDescription;

    private String taskAssignees;

    private Object taskStatus;

    private Timestamp createdOn;

    private Timestamp updatedOn;


}
