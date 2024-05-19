package com.example.int221integratedkk1_backend.Controllers;

import com.example.int221integratedkk1_backend.DTOS.TaskDTO;
import com.example.int221integratedkk1_backend.DTOS.TaskRequest;
import com.example.int221integratedkk1_backend.Entities.TaskEntity;
import com.example.int221integratedkk1_backend.Exception.ItemNotFoundException;
import com.example.int221integratedkk1_backend.Services.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/v2/tasks")
@CrossOrigin(origins = "http://localhost:5173/")
public class TaskController {


    @Autowired
    private TaskService taskService;

    @GetMapping("")
    public ResponseEntity<List<TaskDTO>> getAllTasks(@RequestParam(required = false) List<String> filterStatuses,
                                                     @RequestParam(defaultValue = "status.name") String sortBy,
                                                     @RequestParam(defaultValue = "asc") String sortDirection) {

        List<TaskDTO> taskDTOs = taskService.getAllTasks(filterStatuses, sortBy, sortDirection);
        return ResponseEntity.ok(taskDTOs);
    }

    @GetMapping("/{id}")
    public TaskEntity getTaskById(@PathVariable int id) {
        return taskService.getTaskById(id);
    }

    @PostMapping
    public ResponseEntity<TaskEntity> createTask(@RequestBody TaskRequest task) {
        TaskEntity createdTask = taskService.createTask(task);
        if (createdTask != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTask(@PathVariable int id, @RequestBody TaskEntity task) {
        boolean isUpdated = taskService.updateTask(id, task);
        if (isUpdated) {
            return ResponseEntity.ok("Task updated successfully");
        } else {
            throw new ItemNotFoundException("Task " + id + "does not exist !!!");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable int id) {
        boolean isDeleted = taskService.deleteTask(id);
        if (isDeleted) {
            return ResponseEntity.ok("Task deleted successfully");
        } else {
            throw new ItemNotFoundException("Task " + id + "does not exist !!!");
        }
    }

}
