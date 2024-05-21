package com.example.int221integratedkk1_backend.Services;

import com.example.int221integratedkk1_backend.DTOS.TaskDTO;
import com.example.int221integratedkk1_backend.DTOS.TaskAddRequest;
import com.example.int221integratedkk1_backend.DTOS.TaskUpdateRequest;
import com.example.int221integratedkk1_backend.Entities.StatusEntity;
import com.example.int221integratedkk1_backend.Entities.TaskEntity;
import com.example.int221integratedkk1_backend.Exception.ItemNotFoundException;
import com.example.int221integratedkk1_backend.Exception.ValidateInputException;
import com.example.int221integratedkk1_backend.Models.ErrorMessageExtractor;
import com.example.int221integratedkk1_backend.Repositories.StatusRepository;
import com.example.int221integratedkk1_backend.Repositories.TaskRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    public TaskEntity createTask(@Valid TaskAddRequest task) {
        if (task.getTitle() == null || task.getTitle().isEmpty()) {
            throw new ValidateInputException("Task title must not be null");
        }
        if (task.getTitle().length() > 100) {
            throw new ValidateInputException("title size must be between 0 and 100\ndescription size must be between 0 and 500\n assignees size must be between 0 and 30");
        }
        if (task.getDescription() != null && task.getDescription().length() > 500) {
            throw new ValidateInputException("title size must be between 0 and 100\ndescription size must be between 0 and 500\n assignees size must be between 0 and 30");
        }
        if (task.getAssignees() != null && task.getAssignees().length() > 30) {
            throw new ValidateInputException("title size must be between 0 and 100\ndescription size must be between 0 and 500\n assignees size must be between 0 and 30");
        }

        if (task.getTitle() != null) {
            task.setTitle(task.getTitle().trim());
        }
        if (task.getDescription() != null) {
            task.setDescription(task.getDescription().trim());
        }

        if (task.getAssignees() != null) {
            task.setAssignees(task.getAssignees().trim());
        }
        TaskEntity taskEntity = modelMapper.map(task, TaskEntity.class);
        StatusEntity statusEntity = statusRepository.findById(task.getStatus())
                .orElseThrow(() -> new ItemNotFoundException("Task does not exist"));
        taskEntity.setStatus(statusEntity);

        return repository.save(taskEntity);
    }


    @Transactional
    public boolean updateTask(Integer id,@Valid TaskUpdateRequest editTask) {
        if (editTask.getTitle() == null || editTask.getTitle().trim().isEmpty()) {
            throw new ValidateInputException("Task title must not be null or empty");
        }

        if (editTask.getTitle().length() > 100) {
            throw new ValidateInputException("title size must be between 0 and 100\ndescription size must be between 0 and 500\nassignees size must be between 0 and 30");
        }
        if (editTask.getDescription() != null && editTask.getDescription().length() > 500) {
            throw new ValidateInputException("title size must be between 0 and 100\ndescription size must be between 0 and 500\nassignees size must be between 0 and 30");
        }
        if (editTask.getAssignees() != null && editTask.getAssignees().length() > 30) {
            throw new ValidateInputException("title size must be between 0 and 100\ndescription size must be between 0 and 500\nassignees size must be between 0 and 30");
        }
        TaskEntity task = repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Task " + id + " does not exist !!!"));

        if (editTask.getStatus() == null) {
            throw new ValidateInputException("Status must be provided");
        }

        Integer statusId = (Integer) editTask.getStatus();
        StatusEntity statusEntity = statusRepository.findById(statusId)
                .orElseThrow(() -> new ItemNotFoundException("Status " + statusId + " does not exist !!!"));

        task.setTitle(editTask.getTitle().trim());
        task.setDescription(editTask.getDescription().trim());
        task.setDescription(editTask.getDescription().trim());
        task.setAssignees(editTask.getAssignees().trim());
        task.setStatus(statusEntity);
        task.setUpdatedOn(ZonedDateTime.now().toOffsetDateTime());
        repository.save(task);
        return true;
    }





//    @Transactional
//    public TaskEntity createTask(@Valid TaskAddRequest task) {
//        TaskEntity taskEntity = modelMapper.map(task, TaskEntity.class);
//        StatusEntity statusEntity = statusRepository.findById(task.getStatus())
//                .orElseThrow(() -> new ItemNotFoundException("Status does not exist"));
//        taskEntity.setStatus(statusEntity);
//
//        return repository.save(taskEntity);
//    }
//
//    @Transactional
//    public boolean updateTask(Integer id, @Valid TaskUpdateRequest editTask) {
//        TaskEntity task = repository.findById(id)
//                .orElseThrow(() -> new ItemNotFoundException("Task " + id + " does not exist !!!"));
//
//        Integer statusId = editTask.getStatus();
//        StatusEntity statusEntity = statusRepository.findById(statusId)
//                .orElseThrow(() -> new ItemNotFoundException("Status " + statusId + " does not exist !!!"));
//
//        task.setTitle(editTask.getTitle().trim());
//        task.setDescription(editTask.getDescription() != null ? editTask.getDescription().trim() : null);
//        task.setAssignees(editTask.getAssignees() != null ? editTask.getAssignees().trim() : null);
//        task.setStatus(statusEntity);
//        task.setUpdatedOn(ZonedDateTime.now().toOffsetDateTime());
//        repository.save(task);
//        return true;
//    }

    @Transactional
    public boolean deleteTask(Integer id) {
        TaskEntity task = repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Task " + id + " does not exist !!!"));
        repository.delete(task);
        return true;
    }

}

