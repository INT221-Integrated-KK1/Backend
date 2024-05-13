package com.example.int221integratedkk1_backend.Services;

import com.example.int221integratedkk1_backend.Entities.StatusEntity;

import com.example.int221integratedkk1_backend.Exception.ItemNotFoundException;

import com.example.int221integratedkk1_backend.Entities.TaskEntity;

import com.example.int221integratedkk1_backend.Repositories.StatusRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;


@Getter
@Setter
@Service
public class StatusService {
    private final StatusRepository statusRepository;

    @Autowired
    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public List<StatusEntity> getAllStatuses() {
        return statusRepository.findAll();
    }
  
    public StatusEntity getStatusById(int id) {
        return statusRepository.findById(id).orElse(null);
    }


    public StatusEntity createStatus(StatusEntity statusEntity) {


        if (statusEntity.getName() != null) {
            statusEntity.setName(statusEntity.getName().trim());
        }
        if (statusEntity.getDescription() != null) {
            statusEntity.setDescription(statusEntity.getDescription().trim());
        }
        return statusRepository.save(statusEntity);
    }
  

    public ResponseEntity<?> updateStatus(int id, StatusEntity updatedStatus) {
        if (updatedStatus.getName() != null) {
            updatedStatus.setName(updatedStatus.getName().trim());
        }
        if (updatedStatus.getDescription() != null) {
            updatedStatus.setDescription(updatedStatus.getDescription().trim());
        }

        StatusEntity existingStatus = statusRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Status with ID " + id + " not found"));


        if (id != 1 && !"No Status".equals(updatedStatus.getName())) {
            updatedStatus.setId(id);
            statusRepository.save(updatedStatus);
            return ResponseEntity.ok("Status updated successfully");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    public void deleteStatus(int id) {
        statusRepository.deleteById(id);
    }
}
