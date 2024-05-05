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
    private int id;
    private String title;
    private String description;
    private String assignees;
    private Object status;
    private ZonedDateTime updateOn;
}
