package com.example.int221integratedkk1_backend.Services;

import com.example.int221integratedkk1_backend.Entities.StatusEntity;
import com.example.int221integratedkk1_backend.Entities.TaskEntity;
import com.example.int221integratedkk1_backend.Exception.*;
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
        if (statusRepository.findByName(statusEntity.getName()).isPresent()) {
            throw new DuplicateStatusException("Status name must be unique");
        }
        return statusRepository.save(statusEntity);
    }

    @Transactional
    public String updateStatus(int id, @Valid StatusEntity updatedStatus) {
        StatusEntity existingStatus = statusRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Status " + id + " not found"));

        if ("No Status".equalsIgnoreCase(existingStatus.getName()) || existingStatus.getId() == 1) {
            throw new UnManageStatusException("Cannot be update 'No Status'");
        }
        if ("Done".equalsIgnoreCase(existingStatus.getName()) || existingStatus.getId() == 7) {
            throw new UnManageStatusException("Cannot be update 'Done'");
        }

        Optional<StatusEntity> duplicateStatus = statusRepository.findByName(updatedStatus.getName().trim());
        if (duplicateStatus.isPresent() && duplicateStatus.get().getId() != existingStatus.getId()) {
            throw new DuplicateStatusException("Status name must be unique");
        }

        existingStatus.setName(updatedStatus.getName());
        existingStatus.setDescription(updatedStatus.getDescription());

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
            throw new UnManageStatusException("Cannot be  delete 'Done'");
        }

        statusRepository.delete(status);
    }

    @Transactional
    public int transferTasksAndDeleteStatus(int id, Integer transferToId) {
        StatusEntity statusToDelete = statusRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Status " + id + " not found"));

        if ("No Status".equalsIgnoreCase(statusToDelete.getName()) || statusToDelete.getId() == 1) {
            throw new UnManageStatusException("Cannot delete 'No Status'");
        }
        if ("Done".equalsIgnoreCase(statusToDelete.getName()) || statusToDelete.getId() == 7) {
            throw new UnManageStatusException("Cannot delete 'Done'");
        }

        if (transferToId != null && transferToId.equals(id)) {
            throw new InvalidTransferIdException("destination status for task transfer must be different from the current status");
        }

        List<TaskEntity> tasks = taskRepository.findByStatusId(id);
        if (!tasks.isEmpty()) {
            if (transferToId == null) {
                throw new InvalidTransferIdException("destination status for task transfer must be different from current status");
            }
            StatusEntity transferToStatus = statusRepository.findById(transferToId)
                    .orElseThrow(() -> new ItemNotFoundException("The specified status for task transfer does not exist"));

            tasks.forEach(task -> task.setStatus(transferToStatus));
            taskRepository.saveAll(tasks);
        }

        statusRepository.delete(statusToDelete);
        return tasks.size();
    }
}
