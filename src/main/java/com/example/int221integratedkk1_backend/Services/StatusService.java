package com.example.int221integratedkk1_backend.Services;

import com.example.int221integratedkk1_backend.Entities.StatusEntity;
import com.example.int221integratedkk1_backend.Entities.TaskEntity;
import com.example.int221integratedkk1_backend.Exception.ItemNotFoundException;
import com.example.int221integratedkk1_backend.Exception.StatusCannotBeDeletedException;
import com.example.int221integratedkk1_backend.Repositories.StatusRepository;
import com.example.int221integratedkk1_backend.Repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    public StatusEntity getStatusById(int id) {
        return statusRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException());
    }

    public StatusEntity createStatus(StatusEntity statusEntity) {

        if (statusEntity.getName() != null) {
            statusEntity.setName(statusEntity.getName().trim());
        }
        if (statusEntity.getDescription() != null) {
            statusEntity.setDescription(statusEntity.getDescription().trim());
        }


        String statusName = statusEntity.getName().toLowerCase();
        Optional<StatusEntity> existingStatus = statusRepository.findById(statusEntity.getId());
        if (existingStatus.isPresent()) {
            return null;
        } else {

            return statusRepository.save(statusEntity);
        }
    }

    public StatusEntity updateStatus(int id, StatusEntity updatedStatus) {
        try {

            StatusEntity existingStatus = statusRepository.findById(id)
                    .orElseThrow(() -> new ItemNotFoundException());


            if (existingStatus.getName().equalsIgnoreCase("No Status")) {
                throw new IllegalArgumentException("Cannot update 'No Status'");
            }


            if (updatedStatus.getDescription() != null && !updatedStatus.getDescription().trim().isEmpty()) {
                existingStatus.setDescription(updatedStatus.getDescription().trim());
            }

            if (updatedStatus.getName() != null && !updatedStatus.getName().trim().isEmpty()) {
                existingStatus.setName(updatedStatus.getName().trim());
            }


            return statusRepository.save(existingStatus);
        } catch (IllegalArgumentException e) {

            return null;
        } catch (ItemNotFoundException e) {

            return null;
        }
    }

    @Transactional
    public void deleteStatus(int id) {
        StatusEntity status = statusRepository.findById(id).orElseThrow(ItemNotFoundException::new);
        if ("No Status".equalsIgnoreCase(status.getName())) {
            throw new StatusCannotBeDeletedException("Cannot delete 'No Status'");
        }
        statusRepository.delete(status);
    }

    @Transactional
    public int transferTasksAndDeleteStatus(int id, Integer transferToId) {
        StatusEntity statusToDelete = statusRepository.findById(id).orElseThrow(ItemNotFoundException::new);
        if ("No Status".equalsIgnoreCase(statusToDelete.getName())) {
            throw new StatusCannotBeDeletedException("Cannot delete 'No Status'");
        }

        List<TaskEntity> tasks = taskRepository.findByStatusId(id);
        if (!tasks.isEmpty()) {
            if (transferToId == null) {
                throw new ItemNotFoundException();
            }
            StatusEntity transferToStatus = statusRepository.findById(transferToId).orElseThrow(ItemNotFoundException::new);
            tasks.forEach(task -> task.setStatus(transferToStatus));
            taskRepository.saveAll(tasks);
        }
        statusRepository.delete(statusToDelete);
        return tasks.size();
    }

}



