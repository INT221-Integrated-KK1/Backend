package com.example.int221integratedkk1_backend.DTOS;

import lombok.Getter;


import lombok.Setter;

@Getter
@Setter

public class TaskRequest {
    private int id;
    private String title;
    private String description;
    private String assignees;
    public Integer status;

}
