package com.example.int221integratedkk1_backend.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Setter
@Getter
@Entity
@Table(name = "task", schema = "ITB-KK-V2")
public class TaskEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "taskId")
    private int id;
    @Basic
    @Column(name = "taskTitle", nullable = false,length = 100)
    private String title;
    @Basic
    @Column(name = "taskDescription",length = 500)
    private String description;
    @Basic
    @Column(name = "taskAssignees",length = 30)
    private String assignees;

    @Basic
    @Column(name = "createdOn", updatable = false, insertable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private OffsetDateTime createdOn;

    @Column(name = "updatedOn", insertable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private OffsetDateTime updatedOn;

    @ManyToOne
    @JoinColumn(name = "taskStatus", referencedColumnName = "statusId", nullable = false, columnDefinition = "varchar(255) default 'No Status'")
    private StatusEntity status;


}
