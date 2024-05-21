package com.example.int221integratedkk1_backend.DTOS;

import jakarta.validation.constraints.Size;
import lombok.Getter;


import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter

public class TaskAddRequest {
    private int id;
    @NotNull
    @Size(max = 100, message = "title size must be between 0 and 100")
    private String title;

    @Size(max = 500, message = "description size must be between 0 and 500")
    private String description;

    @Size(max = 30, message = "assignees size must be between 0 and 30")
    private String assignees;

    public Integer status;


}
