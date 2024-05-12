//package com.example.int221integratedkk1_backend.Entities;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.OffsetDateTime;
//import java.time.ZonedDateTime;
//
//@Getter
//@Setter
//@Entity
//@Table(name = "task", schema = "itb-kk")
//public class Task {
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Id
//    @Column(name = "taskId")
//    private int id;
//
//    @Column(name = "taskTitle", nullable = false,length = 100)
//    private String title;
//
//    @Column(name = "taskDescription",length = 500)
//    private String description;
//
//    @Column(name = "taskAssignees",length = 30)
//    private String assignees;
//
//    @Column(name = "taskStatus", columnDefinition = "varchar(255) default 'No Status'")
//    private Object status;
//
//    @Column(name = "createdOn", updatable = false, insertable = false)
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
//    private OffsetDateTime createdOn;
//
//    @Column(name = "updatedOn", insertable = false)
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
//    private OffsetDateTime updatedOn;
//
//    public void setTitle(String value) {
//        this.title = value.trim();
//    }
//
//    public void setDescription(String value) {
//        if (value == null || value.isEmpty()) {
//            this.description = null;
//        } else
//            this.description = value.trim();
//    }
//    public void setAssignees(String value) {
//        if (value == null || value.isEmpty()) {
//            this.assignees = null;
//        } else
//            this.assignees = value.trim();
//    }
//
//}
