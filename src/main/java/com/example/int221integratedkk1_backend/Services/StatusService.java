package com.example.int221integratedkk1_backend.Services;

import com.example.int221integratedkk1_backend.Entities.StatusEntity;
import com.example.int221integratedkk1_backend.Entities.TaskEntity;
import com.example.int221integratedkk1_backend.Exception.ItemNotFoundException;
import com.example.int221integratedkk1_backend.Exception.StatusCannotBeDeletedException;
import com.example.int221integratedkk1_backend.Exception.ValidateInputException;
import com.example.int221integratedkk1_backend.Models.ErrorMessageExtractor;
import com.example.int221integratedkk1_backend.Repositories.StatusRepository;
import com.example.int221integratedkk1_backend.Repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public StatusEntity createStatus(StatusEntity statusEntity) {
        if (statusEntity.getName() != null) {
            statusEntity.setName(statusEntity.getName().trim());
        }
        if (statusEntity.getDescription() != null) {
            statusEntity.setDescription(statusEntity.getDescription().trim());
        }

        return statusRepository.save(statusEntity);
    }

    @Transactional
    public StatusEntity updateStatus(int id, StatusEntity updatedStatus) {
        StatusEntity existingStatus = statusRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Status " + id + " not found"));

        if ("No Status".equalsIgnoreCase(existingStatus.getName())) {
            throw new IllegalArgumentException("Cannot update 'No Status'");
        }
        if ("Done".equalsIgnoreCase(existingStatus.getName())) {
            throw new IllegalArgumentException("Cannot update 'Done'");
        }

        if (updatedStatus.getDescription() != null && !updatedStatus.getDescription().trim().isEmpty()) {
            existingStatus.setDescription(updatedStatus.getDescription().trim());
        }
        if (updatedStatus.getName() != null && !updatedStatus.getName().trim().isEmpty()) {
            existingStatus.setName(updatedStatus.getName().trim());
        }

        return statusRepository.save(existingStatus);
    }

    //    @Transactional
//    public StatusEntity createStatus(StatusEntity statusEntity) {
//        if (statusEntity.getName() != null) {
//            statusEntity.setName(statusEntity.getName().trim());
//        }
//        if (statusEntity.getDescription() != null) {
//            statusEntity.setDescription(statusEntity.getDescription().trim());
//        }
//
//        try {
//            return statusRepository.save(statusEntity);
//        } catch (DataIntegrityViolationException ex) {
//            String errorMessage = ex.getMessage();
//            String extractedMessage = errorMessageExtractor.extractErrorMessage(errorMessage);
//            throw new ValidateInputException(extractedMessage);
//        }
//    }
//    @Transactional
//    public StatusEntity updateStatus(int id, StatusEntity updatedStatus) {
//        StatusEntity existingStatus = statusRepository.findById(id)
//                .orElseThrow(() -> new ItemNotFoundException("Status " + id + " not found"));
//
//        if ("No Status".equalsIgnoreCase(existingStatus.getName())) {
//            throw new IllegalArgumentException("Cannot update 'No Status'");
//        }
//        if ("Done".equalsIgnoreCase(existingStatus.getName())) {
//            throw new IllegalArgumentException("Cannot update 'Done'");
//        }
//
//        if (updatedStatus.getDescription() != null && !updatedStatus.getDescription().trim().isEmpty()) {
//            existingStatus.setDescription(updatedStatus.getDescription().trim());
//        }
//        if (updatedStatus.getName() != null && !updatedStatus.getName().trim().isEmpty()) {
//            existingStatus.setName(updatedStatus.getName().trim());
//        }
//
//        try {
//            return statusRepository.save(existingStatus);
//        } catch (DataIntegrityViolationException ex) {
//            String errorMessage = ex.getMessage();
//            String extractedMessage = errorMessageExtractor.extractErrorMessage(errorMessage);
//            throw new ValidateInputException(extractedMessage);
//        }
//    }
    @Transactional
    public void deleteStatus(int id) {
        StatusEntity status = statusRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Status " + id + " not found"));

        if ("No Status".equalsIgnoreCase(status.getName())) {
            throw new StatusCannotBeDeletedException("Cannot delete 'No Status'");
        }
        if ("Done".equalsIgnoreCase(status.getName())) {
            throw new StatusCannotBeDeletedException("Cannot delete 'Done'");
        }

        statusRepository.delete(status);
    }

    @Transactional
    public int transferTasksAndDeleteStatus(int id, Integer transferToId) {
        StatusEntity statusToDelete = statusRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Status " + id + " not found"));

        if ("No Status".equalsIgnoreCase(statusToDelete.getName())) {
            throw new StatusCannotBeDeletedException("Cannot delete 'No Status'");
        }
        if ("Done".equalsIgnoreCase(statusToDelete.getName())) {
            throw new StatusCannotBeDeletedException("Cannot delete 'Done'");
        }

        List<TaskEntity> tasks = taskRepository.findByStatusId(id);
        if (!tasks.isEmpty()) {
            if (transferToId == null) {
                throw new ItemNotFoundException("Transfer status not found");
            }
            StatusEntity transferToStatus = statusRepository.findById(transferToId)
                    .orElseThrow(() -> new ItemNotFoundException("Status " + transferToId + " not found"));
            tasks.forEach(task -> task.setStatus(transferToStatus));
            taskRepository.saveAll(tasks);
        }
        statusRepository.delete(statusToDelete);
        return tasks.size();
    }
}
