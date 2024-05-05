package com.example.int221integratedkk1_backend.DTOS;

import lombok.*;

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


