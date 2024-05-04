package com.example.int221integratedkk1_backend.Controllers;

import com.example.int221integratedkk1_backend.DTOS.AddDTO;
import com.example.int221integratedkk1_backend.DTOS.EditDTO;
import com.example.int221integratedkk1_backend.DTOS.ErrorDTO;
import com.example.int221integratedkk1_backend.DTOS.TaskDTO;
import com.example.int221integratedkk1_backend.Entities.Task;
import com.example.int221integratedkk1_backend.Exception.ItemNotFoundException;
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
        try {
            Task task = taskService.getTaskById(id);
            return ResponseEntity.ok(task);
        } catch (ItemNotFoundException e) {
            String errorMessage = e.getMessage();
            ErrorDTO errorDetails = new ErrorDTO(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), errorMessage, "/api/tasks/" + id);
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<TaskDTO> addTask(@RequestBody AddDTO addTaskDTO) {
        TaskDTO savedTask = taskService.addTask(addTaskDTO);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Integer id, @RequestBody EditDTO editTaskDTO) {
        try {
            boolean updated = taskService.updateTask(id, editTaskDTO);
            return ResponseEntity.ok("Task update successfully");
        } catch (ItemNotFoundException e) {
            String errorMessage = e.getMessage();
            ErrorDTO errorDetails = new ErrorDTO(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), errorMessage, "/v1/tasks/" + id);
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }

}

