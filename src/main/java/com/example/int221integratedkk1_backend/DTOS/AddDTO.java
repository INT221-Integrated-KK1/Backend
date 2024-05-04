package com.example.int221integratedkk1_backend.DTOS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AddDTO {
    private String taskTitle;
    private String taskDescription;
    private String taskAssignees;
    private Object taskStatus;
}