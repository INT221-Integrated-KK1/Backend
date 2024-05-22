package com.example.int221integratedkk1_backend.Services;

import com.example.int221integratedkk1_backend.Entities.StatusEntity;
import com.example.int221integratedkk1_backend.Entities.TaskEntity;
import com.example.int221integratedkk1_backend.Exception.*;
import com.example.int221integratedkk1_backend.Models.ErrorMessageExtractor;
import com.example.int221integratedkk1_backend.Repositories.StatusRepository;
import com.example.int221integratedkk1_backend.Repositories.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {
    private final StatusRepository statusRepository;
    private final TaskRepository taskRepository;
    private ErrorMessageExtractor errorMessageExtractor;

    @Autowired
    public StatusService(StatusRepository statusRepository, TaskRepository taskRepository) {
        this.statusRepository = statusRepository;
        this.taskRepository = taskRepository;
    }

    public List<StatusEntity> getAllStatuses() {
        return statusRepository.findAll();
    }

    public StatusEntity getStatusById(int id) {
        return statusRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Status " + id + " not found"));
    }

    @Transactional
    public StatusEntity createStatus(@Valid StatusEntity statusEntity) {
        if (statusEntity.getName() == null || statusEntity.getName().trim().isEmpty()) {
            throw new ValidateInputException("Status name must not be null or empty");
        }

        if (statusEntity.getName().length() > 50) {
            throw new ValidateInputException("name size must be between 0 and 50\ndescription size must be between 0 and 200");
        }
        if (statusEntity.getDescription() != null && statusEntity.getDescription().length() > 200) {
            throw new ValidateInputException("description size must be between 0 and 50\ndescription size must be between 0 and 200");
        }
        if (statusEntity.getName() != null) {
            statusEntity.setName(statusEntity.getName().trim());
        }
        if (statusEntity.getDescription() != null) {
            statusEntity.setDescription(statusEntity.getDescription().trim());
        }
        if (statusRepository.findByName(statusEntity.getName()).isPresent()) {
            throw new DuplicateStatusException("Status name must be unique");
        }

        return statusRepository.save(statusEntity);
    }

    @Transactional
    public String updateStatus(int id, @Valid StatusEntity updatedStatus) {
        if (updatedStatus.getName() == null || updatedStatus.getName().trim().isEmpty()) {
            throw new ValidateInputException("Status name must not be null or empty");
        }
        if (updatedStatus.getName().length() > 50) {
            throw new ValidateInputException("name size must be between 0 and 50\ndescription size must be between 0 and 200");
        }

        if (updatedStatus.getDescription() != null && updatedStatus.getDescription().length() > 200) {
            throw new ValidateInputException("description size must be between 0 and 50\ndescription size must be between 0 and 200");
        }

        StatusEntity existingStatus = statusRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Status " + id + " not found"));

        // Check for restricted status names
        if ("No Status".equalsIgnoreCase(existingStatus.getName()) || existingStatus.getId() == 1) {
            throw new UnManageStatusException("Cannot be update 'No Status'");
        }
        if ("Done".equalsIgnoreCase(existingStatus.getName()) || existingStatus.getId() == 7) {
            throw new UnManageStatusException("Cannot be update 'done'");
        }

        // Check for unique status name
        Optional<StatusEntity> duplicateStatus = statusRepository.findByName(updatedStatus.getName().trim());
        if (duplicateStatus.isPresent() && Integer.valueOf(duplicateStatus.get().getId()) != Integer.valueOf(existingStatus.getId())) {
            throw new DuplicateStatusException("Status name must be unique");
        }

        // Update the status entity
        if (updatedStatus.getDescription() != null && !updatedStatus.getDescription().trim().isEmpty()) {
            existingStatus.setDescription(updatedStatus.getDescription().trim());
        }

        if (updatedStatus.getDescription() == null) {
            existingStatus.setDescription("");
        }

        if (updatedStatus.getName() != null && !updatedStatus.getName().trim().isEmpty()) {
            existingStatus.setName(updatedStatus.getName().trim());
        }

        statusRepository.save(existingStatus);
        return "Status has been updated";
    }

    @Transactional
    public void deleteStatus(int id) {
        StatusEntity status = statusRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Status " + id + " not found"));

        if ("No Status".equalsIgnoreCase(status.getName())) {
            throw new UnManageStatusException("Cannot be delete 'No Status'");
        }
        if ("Done".equalsIgnoreCase(status.getName())) {
            throw new UnManageStatusException("Cannot be delete 'done'");
        }

        statusRepository.delete(status);
    }

    @Transactional
    public int transferTasksAndDeleteStatus(int id, Integer transferToId) {
        StatusEntity statusToDelete = statusRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Status " + id + " not found"));

        // Check for restricted status names
        if ("No Status".equalsIgnoreCase(statusToDelete.getName()) || statusToDelete.getId() == 1) {
            throw new UnManageStatusException("Cannot delete 'No Status'");
        }
        if ("Done".equalsIgnoreCase(statusToDelete.getName()) || statusToDelete.getId() == 7) {
            throw new UnManageStatusException("Cannot delete 'Done'");
        }

        // Check that the transferToId is not the same as the id being deleted
        if (transferToId != null && transferToId.equals(id)) {
            throw new InvalidTransferIdException("destination status for task transfer must be different from current status");
        }

        // Find tasks associated with the status to be deleted
        List<TaskEntity> tasks = taskRepository.findByStatusId(id);
        if (!tasks.isEmpty()) {
            if (transferToId == null) {
                throw new ItemNotFoundException("The specified status for task transfer does not exist");
            }
            StatusEntity transferToStatus = statusRepository.findById(transferToId)
                    .orElseThrow(() -> new ItemNotFoundException("The specified status for task transfer does not exist"));

            // Transfer tasks to the new status
            tasks.forEach(task -> task.setStatus(transferToStatus));
            taskRepository.saveAll(tasks);
        }

        // Delete the status
        statusRepository.delete(statusToDelete);
        return tasks.size();
    }

}
