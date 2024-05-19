package com.example.int221integratedkk1_backend.Controllers;

import com.example.int221integratedkk1_backend.Entities.StatusEntity;
import com.example.int221integratedkk1_backend.Exception.ItemNotFoundException;
import com.example.int221integratedkk1_backend.Exception.StatusCannotBeDeletedException;
import com.example.int221integratedkk1_backend.Services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/statuses")
@CrossOrigin(origins = "http://localhost:5173/")
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
    public ResponseEntity<StatusEntity> getStatusById(@PathVariable int id) {
        StatusEntity status = statusService.getStatusById(id);
        return ResponseEntity.ok(status);
    }

    @PostMapping
    public ResponseEntity<StatusEntity> createStatus(@RequestBody StatusEntity statusEntity) {
        StatusEntity createdStatus = statusService.createStatus(statusEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStatus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StatusEntity> updateStatus(@PathVariable int id, @RequestBody StatusEntity updatedStatus) {
        StatusEntity updatedEntity = statusService.updateStatus(id, updatedStatus);
        return ResponseEntity.ok(updatedEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStatus(@PathVariable int id) {
        statusService.deleteStatus(id);
        return ResponseEntity.ok("The status has been deleted");
    }

    @DeleteMapping("/{id}/{newId}")
    public ResponseEntity<String> transferAndDeleteStatus(@PathVariable int id, @PathVariable int newId) {
        int transferredTasks = statusService.transferTasksAndDeleteStatus(id, newId);
        return ResponseEntity.ok(transferredTasks + " task(s) have been transferred and the status has been deleted");
    }
}
