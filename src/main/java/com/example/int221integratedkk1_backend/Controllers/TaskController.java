package com.example.int221integratedkk1_backend.Controllers;

import com.example.int221integratedkk1_backend.DTOS.ErrorDTO;
import com.example.int221integratedkk1_backend.DTOS.TaskDTO;
import com.example.int221integratedkk1_backend.Entities.TaskEntity;
import com.example.int221integratedkk1_backend.Exception.ItemNotFoundException;
import com.example.int221integratedkk1_backend.Services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<TaskDTO> taskDTOs = taskService.getAllTasks();
        if (taskDTOs.isEmpty()) {
            return ResponseEntity.ok().body(List.of());
        } else {
            return ResponseEntity.ok().body(taskDTOs);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Integer id) {
        try {
            TaskEntity task = taskService.getTaskById(id);
            return ResponseEntity.ok(task);
        } catch (ItemNotFoundException e) {
            String errorMessage = e.getMessage();
            ErrorDTO errorDetails = new ErrorDTO(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), errorMessage, "/api/tasks/" + id);
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public TaskEntity createTask(@RequestBody TaskEntity task) {
        TaskEntity savedTask = taskService.createTask(task);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED).getBody();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Integer id, @RequestBody TaskEntity task) {
        task.setId(id);
        try {
            boolean updated = taskService.updateTask(id,task);
            return ResponseEntity.ok("Task update successfully");
        } catch (ItemNotFoundException e) {
            String errorMessage = e.getMessage();
            ErrorDTO errorDetails = new ErrorDTO(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), errorMessage, "/v1/tasks/" + id);
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Integer id) {
        try {
            boolean isDeleted = taskService.deleteTaskById(id);
            return ResponseEntity.ok("Task deleted successfully");
        } catch (ItemNotFoundException e) {
            String errorMessage = e.getMessage();
            ErrorDTO errorDetails = new ErrorDTO(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), errorMessage, "/v1/tasks/" + id);
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }


}
