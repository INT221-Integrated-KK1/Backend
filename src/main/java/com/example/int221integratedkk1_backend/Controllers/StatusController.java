package com.example.int221integratedkk1_backend.Controllers;

import com.example.int221integratedkk1_backend.Entities.StatusEntity;
import com.example.int221integratedkk1_backend.Services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v2/statuses")
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
    public Optional<StatusEntity> getStatusById(@PathVariable int id) {
        return statusService.getStatusById(id);
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


}
