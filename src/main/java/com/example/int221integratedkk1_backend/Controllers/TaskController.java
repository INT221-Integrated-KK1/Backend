package com.example.int221integratedkk1_backend.Controllers;

import com.example.int221integratedkk1_backend.DTOS.ErrorDTO;
import com.example.int221integratedkk1_backend.DTOS.TaskDTO;
import com.example.int221integratedkk1_backend.Entities.Task;
import com.example.int221integratedkk1_backend.Services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
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
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<TaskDTO> tasks = taskService.getAllTasks();
        if (tasks.isEmpty()) {
            return ResponseEntity.ok().body(List.of());
        } else {
            return ResponseEntity.ok().body(tasks);
        }
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

    @PostMapping
    public ResponseEntity<?> addTask(@RequestBody TaskDTO taskDTO) {
        TaskDTO createdTask = taskService.addTask(taskDTO);
        if (createdTask != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create task");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Integer id) {
        boolean isDeleted = taskService.deleteTaskById(id);

        if (isDeleted) {
            return ResponseEntity.ok("Task deleted successfully");
        } else {
            String errorMessage = "NOT FOUND";
            ErrorDTO errorDetails = new ErrorDTO(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), errorMessage, "/v1/tasks/" + id);
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Integer id, @RequestBody Task updatedTask) {
        boolean isUpdated = taskService.updateTask(id, updatedTask);

        if (isUpdated) {
            return ResponseEntity.ok("The task has been updated");
        } else {
            String errorMessage = "NOT FOUND";
            ErrorDTO errorDetails = new ErrorDTO(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), errorMessage, "/v1/tasks/" + id);
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }

}

