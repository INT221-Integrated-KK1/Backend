package com.example.int221integratedkk1_backend.Controllers;

import com.example.int221integratedkk1_backend.DTOS.ErrorDTO;
import com.example.int221integratedkk1_backend.Entities.StatusEntity;
import com.example.int221integratedkk1_backend.Exception.ItemNotFoundException;
import com.example.int221integratedkk1_backend.Entities.TaskEntity;

import com.example.int221integratedkk1_backend.Services.StatusService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RestController
@RequestMapping("/v2/statuses")
@CrossOrigin(origins = "http://localhost:5173")
public class StatusController {
    private final StatusService statusService;

    @Autowired
    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping
    public List<StatusEntity> getAllStatuses() {
        return statusService.getAllStatuses();
    }

    @GetMapping("/{id}")
    public StatusEntity  getStatusById(@PathVariable int id) {
        return statusService.getStatusById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StatusEntity createStatus(@RequestBody StatusEntity statusEntity) {
        return statusService.createStatus(statusEntity);
    }

    @GetMapping("/{id}")
    public StatusEntity  getStatusById(@PathVariable int id) {
        return statusService.getStatusById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable int id, @RequestBody StatusEntity updatedStatus) {
        updatedStatus.setId(id);
        try {
            ResponseEntity<?> updated =statusService.updateStatus(id, updatedStatus);
            return ResponseEntity.ok("Task update successfully");
        } catch (ItemNotFoundException e) {
            String errorMessage = e.getMessage();
            ErrorDTO errorDetails = new ErrorDTO(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), errorMessage, "/v2/statuses/" + id);
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public void deleteStatus(@PathVariable int id) {
        statusService.deleteStatus(id);
    }
}
