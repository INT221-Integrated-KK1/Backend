package com.example.int221integratedkk1_backend.DTOS;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AddDTO {
    private String title;
    private String description;
    private String assignees;
    private Object status;
//    @Column(name = "createdOn", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
//    private ZonedDateTime createdOn;
//    @Column(name = "updatedOn", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
//    private ZonedDateTime updatedOn;
}