package com.example.int221integratedkk1_backend.Services;

import com.example.int221integratedkk1_backend.Entities.StatusEntity;
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

        if (statusRepository.existsById(id)) {
            StatusEntity existingStatus = statusRepository.findById(id).orElse(null);
            if (existingStatus != null) {
                if (id != 1 && !"No Status".equals(updatedStatus.getName())) {
                    updatedStatus.setId(id);
                    return ResponseEntity.ok(statusRepository.save(updatedStatus));
                } else {
                    // Return bad request if attempting to modify statusId 1 or the name "No Status"
                    return ResponseEntity.badRequest().build();
                }
            }
        }
        // Return not found if the status with the given ID does not exist
        return ResponseEntity.notFound().build();
    }




    public void deleteStatus(int id) {
        statusRepository.deleteById(id);
    }
}
