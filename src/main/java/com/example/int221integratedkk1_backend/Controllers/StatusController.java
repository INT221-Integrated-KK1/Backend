package com.example.int221integratedkk1_backend.Controllers;

import com.example.int221integratedkk1_backend.Entities.StatusEntity;
import com.example.int221integratedkk1_backend.Entities.TaskEntity;
import com.example.int221integratedkk1_backend.Services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public StatusEntity createStatus(@RequestBody StatusEntity statusEntity) {
        return statusService.createStatus(statusEntity);
    }

    @PutMapping("/{id}")
    public StatusEntity updateStatus(@PathVariable int id, @RequestBody StatusEntity updatedStatus) {
        return statusService.updateStatus(id, updatedStatus);
    }

//    @DeleteMapping("/{id}")
//    public void deleteStatus(@PathVariable int id) {
//        statusService.deleteStatus(id);
//    }
}
