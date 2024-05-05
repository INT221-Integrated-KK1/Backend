package com.example.int221integratedkk1_backend.DTOS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditDTO {
    private int taskId;
    private String taskTitle;
    private String taskDescription;
    private String taskAssignees;
    private Object taskStatus;
    private ZonedDateTime updateOn;
}
