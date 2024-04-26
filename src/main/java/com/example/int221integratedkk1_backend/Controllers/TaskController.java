package com.example.int221integratedkk1_backend.Controllers;

import com.example.int221integratedkk1_backend.DTOS.ErrorDTO;
import com.example.int221integratedkk1_backend.DTOS.TaskDTO;
import com.example.int221integratedkk1_backend.Services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/v1/tasks")
public class TaskController {
    @Autowired

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Integer id) {
        TaskDTO taskDTO = taskService.getTaskById(id);
        if (taskDTO != null) {
            return new ResponseEntity<>(taskDTO, HttpStatus.OK);
        } else {
            String errorMessage = "Task Id " + id + " does not exist!!!";
            ErrorDTO errorDetails = new ErrorDTO(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), errorMessage, "/v1/tasks/" + id);
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }
}

