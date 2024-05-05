package com.example.int221integratedkk1_backend.DTOS;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class TaskDTO {
    private int id;
    private String title;
    private String assignees;
    private Object status;
}


