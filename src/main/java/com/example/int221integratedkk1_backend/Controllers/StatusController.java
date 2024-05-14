package com.example.int221integratedkk1_backend.Controllers;

import com.example.int221integratedkk1_backend.Entities.StatusEntity;
import com.example.int221integratedkk1_backend.Services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public StatusEntity createStatus(@RequestBody StatusEntity statusEntity) {
        return statusService.createStatus(statusEntity);
    }

    @PutMapping("/{id}")
    public StatusEntity updateStatus(@PathVariable int id, @RequestBody StatusEntity updatedStatus) {
        return statusService.updateStatus(id, updatedStatus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStatus(@PathVariable int id) {
        ResponseEntity<String> response = statusService.deleteStatus(id);
        if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(response.getBody());
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }
    }

    @PostMapping("/{id}/{newId}")
    public ResponseEntity<String> transferStatus(@PathVariable int id, @PathVariable int newId) {
        ResponseEntity<String> response = statusService.transferAndDeleteStatus(id, newId);
        if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(response.getBody());
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }
    }

}
