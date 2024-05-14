package com.example.int221integratedkk1_backend.Services;

import com.example.int221integratedkk1_backend.Entities.StatusEntity;
import com.example.int221integratedkk1_backend.Entities.TaskEntity;
import com.example.int221integratedkk1_backend.Exception.ItemNotFoundException;
import com.example.int221integratedkk1_backend.Repositories.StatusRepository;
import com.example.int221integratedkk1_backend.Repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {
    private final StatusRepository statusRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public List<StatusEntity> getAllStatuses() {
        return statusRepository.findAll();
    }

    public Optional<StatusEntity> getStatusById(int id) {
        return statusRepository.findById(id);
    }

    public StatusEntity createStatus(StatusEntity statusEntity) {
        return statusRepository.save(statusEntity);
    }

    public StatusEntity updateStatus(int id, StatusEntity updatedStatus) {
        if (statusRepository.existsById(id)) {
            updatedStatus.setId(id);
            return statusRepository.save(updatedStatus);
        } else {

            return null;
        }
    }

    public ResponseEntity<String> deleteStatus(int statusId) {
        StatusEntity status = statusRepository.findById(statusId).orElse(null);
        if (status == null) {
            return ResponseEntity.notFound().build();
        }

        if ("No Status".equals(status.getName())) {
            return ResponseEntity.badRequest().body("The 'No Status' status cannot be deleted.");
        }

        List<TaskEntity> tasks = taskRepository.findByStatus(status);
        if (tasks.isEmpty()) {
            statusRepository.delete(status);
            return ResponseEntity.ok("The status has been deleted.");
        } else {
            return ResponseEntity.ok("There are tasks under this status. Please transfer them to another status before deleting.");
        }
    }

    public ResponseEntity<String> transferAndDeleteStatus(int statusId, int destinationStatusId) {
        StatusEntity status = statusRepository.findById(statusId).orElse(null);
        StatusEntity destinationStatus = statusRepository.findById(destinationStatusId).orElse(null);
        if (status == null || destinationStatus == null) {
            return ResponseEntity.notFound().build();
        }

        if ("No Status".equals(status.getName())) {
            return ResponseEntity.badRequest().body("The 'No Status' status cannot be deleted.");
        }

        List<TaskEntity> tasks = taskRepository.findByStatus(status);
        if (tasks.isEmpty()) {
            statusRepository.delete(status);
            return ResponseEntity.ok("The status has been deleted.");
        } else {
            // Transfer tasks to destination status
            for (TaskEntity task : tasks) {
                task.setStatus(destinationStatus);
            }
            taskRepository.saveAll(tasks);
            statusRepository.delete(status);
            return ResponseEntity.ok(tasks.size() + " task(s) have been transferred and the status has been deleted.");
        }
    }


}
