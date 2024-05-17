package com.example.int221integratedkk1_backend.Controllers;

import com.example.int221integratedkk1_backend.DTOS.TaskDTO;
import com.example.int221integratedkk1_backend.DTOS.TaskRequest;
import com.example.int221integratedkk1_backend.Entities.TaskEntity;
import com.example.int221integratedkk1_backend.Services.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/v2/tasks")
@CrossOrigin(origins = "http://localhost:5173/")
public class TaskController {

    private static final Logger LOGGER = Logger.getLogger(TaskController.class.getName());

    @Autowired
    private TaskService taskService;
    @Autowired
    private ModelMapper modelMapper;


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
    public TaskEntity updateTask(@PathVariable int id, @RequestBody TaskEntity task) {
        task.setId(id);
        return taskService.updateTask(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable int id) {
        taskService.deleteTask(id);
    }

    // filter
//    @GetMapping("")
//    public List<TaskEntity> getTasks(@RequestParam List<String> filterStatuses,
//                                     @RequestParam String sortBy,
//                                     @RequestParam String sortDirection) {
//        return taskService.getTasksByStatusesAndSort(filterStatuses, sortBy, sortDirection);
//    }

}
