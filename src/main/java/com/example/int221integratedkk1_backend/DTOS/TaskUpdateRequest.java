package com.example.int221integratedkk1_backend.DTOS;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskUpdateRequest {

    @NotNull(message = "Task title must not be null or empty")
    @Size(max = 100, message = "title size must be between 0 and 100")
    private String title;

    @Size(max = 500, message = "กescription size must be between 0 and 500")
    private String description;

    @Size(max = 30, message = "assignees size must be between 0 and 30")
    private String assignees;

    @NotNull(message = "Status must be provided")
    private Integer status;

}
