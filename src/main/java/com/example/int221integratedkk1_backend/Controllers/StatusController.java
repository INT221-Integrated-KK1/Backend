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
import java.util.Optional;

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
    public ResponseEntity<Optional<StatusEntity>> getStatusById(@PathVariable int id) {
        try {
            Optional<StatusEntity> status = Optional.ofNullable(statusService.getStatusById(id));
            return ResponseEntity.ok(status);
        } catch (ItemNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<StatusEntity> createStatus(@RequestBody StatusEntity statusEntity) {
        try {
            StatusEntity createdStatus = statusService.createStatus(statusEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStatus);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<StatusEntity> updateStatus(@PathVariable int id, @RequestBody StatusEntity updatedStatus) {
        StatusEntity updatedEntity = statusService.updateStatus(id, updatedStatus);
        if (updatedEntity != null) {
            return ResponseEntity.ok(updatedEntity);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStatus(@PathVariable int id) {
        try {
            statusService.deleteStatus(id);
            return ResponseEntity.ok("The status has been deleted");
        } catch (ItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("An error has occurred, the status does not exist");
        } catch (StatusCannotBeDeletedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/{newId}")
    public ResponseEntity<String> transferAndDeleteStatus(@PathVariable int id, @PathVariable int newId) {
        try {
            int transferredTasks = statusService.transferTasksAndDeleteStatus(id, newId);
            return ResponseEntity.ok(transferredTasks + " task(s) have been transferred and the status has been deleted");
        } catch (ItemNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
