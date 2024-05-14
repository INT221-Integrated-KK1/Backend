package com.example.int221integratedkk1_backend.DTOS;

import lombok.Data;
import lombok.Getter;



import com.example.int221integratedkk1_backend.Entities.StatusEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Setter;

@Getter
@Setter


public class TaskRequest {
    private int id;
    private String title;

    private String description;
    private String assignees;

    private Integer status;

    public void setDescription(String description) {
        if (description == null || description.isEmpty()){
            this.description = null;
    }
        else {
            this.description = description.trim();
        }
    }

    public void setAssignees(String assignees) {
        if (assignees == null || assignees.isEmpty()){
            this.assignees = null;
        }
        else {
            this.assignees = assignees.trim();
        }
    }
}
