package com.example.int221integratedkk1_backend.DTOS;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

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


