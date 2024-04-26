package com.example.int221integratedkk1_backend.DTOS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class TaskDTO {

    private int taskId;

    private String taskTitle;

    private String taskAssignees;

    private Object taskStatus;


}
