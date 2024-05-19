package com.example.int221integratedkk1_backend.Services;

import com.example.int221integratedkk1_backend.DTOS.TaskDTO;
import com.example.int221integratedkk1_backend.DTOS.TaskRequest;
import com.example.int221integratedkk1_backend.Entities.StatusEntity;
import com.example.int221integratedkk1_backend.Entities.TaskEntity;
import com.example.int221integratedkk1_backend.Exception.ItemNotFoundException;
import com.example.int221integratedkk1_backend.Exception.ValidateInputException;
import com.example.int221integratedkk1_backend.Models.ErrorMessageExtractor;
import com.example.int221integratedkk1_backend.Repositories.StatusRepository;
import com.example.int221integratedkk1_backend.Repositories.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.logging.Logger;


@Service
public class TaskService {

    private static final Logger LOGGER = Logger.getLogger(TaskService.class.getName());
    @Autowired
    private TaskRepository repository;
    @Autowired
    private ListMapper listMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StatusRepository statusRepository;
    private ErrorMessageExtractor errorMessageExtractor;

    public List<TaskDTO> getAllTasks(List<String> filterStatuses, String sortBy, String sortDirection) {
        List<TaskEntity> tasks;
        Sort.Direction direction = null;
        if (sortDirection != null && !sortDirection.trim().isEmpty()) {
            String directionUpper = sortDirection.trim().toUpperCase();
            if ("ASC".equals(directionUpper)) {
                direction = Sort.Direction.ASC;
            } else if ("DESC".equals(directionUpper)) {
                direction = Sort.Direction.DESC;
            } else {
                throw new IllegalArgumentException("Invalid value for sortDirection: " + sortDirection);
            }
        } else {

            direction = Sort.Direction.ASC;
        }

        Sort sort = Sort.by(direction, sortBy);

        if (filterStatuses != null && !filterStatuses.isEmpty()) {
            tasks = repository.findAllByStatusNames(filterStatuses, sort);
            LOGGER.info("Filtered tasks by statuses: " + filterStatuses);
        } else {
            tasks = repository.findAll(sort);
            LOGGER.info("Retrieved all tasks sorted by " + sortBy + " in " + sortDirection + " order");
        }
        return listMapper.mapList(tasks, TaskDTO.class);
    }

    public TaskEntity getTaskById(int taskId) {
        return repository.findById(taskId).orElseThrow(() -> new ItemNotFoundException("Task " + taskId + " does not exist !!!"));
    }

    @Transactional
    public TaskEntity createTask(TaskRequest task) {
        TaskEntity taskEntity = modelMapper.map(task, TaskEntity.class);
        StatusEntity statusEntity = statusRepository.findById(task.getStatus())
                .orElseThrow(() -> new ItemNotFoundException("Status does not exist"));
        if (task.getTitle() == null) {
            throw new IllegalArgumentException("Title cannot be null");
        } else
            taskEntity.setStatus(statusEntity);
        return repository.save(taskEntity);
    }

    @Transactional
    public boolean updateTask(Integer id, TaskEntity editTask) {
        TaskEntity task = repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Task " + id + " does not exist !!!"));
        task.setTitle(editTask.getTitle());
        task.setDescription(editTask.getDescription());
        task.setAssignees(editTask.getAssignees());
        task.setStatus(editTask.getStatus());
        task.setUpdatedOn(ZonedDateTime.now().toOffsetDateTime());
        repository.save(task);
        return true;
    }

//@Transactional
//public TaskEntity createTask(TaskRequest task) {
//    try {
//        TaskEntity taskEntity = modelMapper.map(task, TaskEntity.class);
//        StatusEntity statusEntity = statusRepository.findById(task.getStatus())
//                .orElseThrow(() -> new ItemNotFoundException("Status does not exist"));
//        if (task.getTitle() == null) {
//            throw new IllegalArgumentException("Title cannot be null");
//        } else
//            taskEntity.setStatus(statusEntity);
//        return repository.save(taskEntity);
//    } catch (DataIntegrityViolationException ex) {
//        String errorMessage = ex.getMessage();
//        String extractedMessage = errorMessageExtractor.extractErrorMessage(errorMessage);
//        throw new ValidateInputException(extractedMessage);
//    }
//}
//
//    @Transactional
//    public boolean updateTask(Integer id, TaskEntity editTask) {
//        try {
//            TaskEntity task = repository.findById(id)
//                    .orElseThrow(() -> new ItemNotFoundException("Task " + id + " does not exist !!!"));
//            task.setTitle(editTask.getTitle());
//            task.setDescription(editTask.getDescription());
//            task.setAssignees(editTask.getAssignees());
//            task.setStatus(editTask.getStatus());
//            task.setUpdatedOn(ZonedDateTime.now().toOffsetDateTime());
//            repository.save(task);
//            return true;
//        } catch (DataIntegrityViolationException ex) {
//            String errorMessage = ex.getMessage();
//            String extractedMessage = errorMessageExtractor.extractErrorMessage(errorMessage);
//            throw new ValidateInputException(extractedMessage);
//        }
//    }

    @Transactional
    public boolean deleteTask(Integer id) {
        TaskEntity task = repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Task " + id + " does not exist !!!"));
        repository.delete(task);
        return true;
    }

}

