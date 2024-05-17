package com.example.int221integratedkk1_backend.Services;

import com.example.int221integratedkk1_backend.DTOS.TaskDTO;
import com.example.int221integratedkk1_backend.DTOS.TaskRequest;
import com.example.int221integratedkk1_backend.Entities.StatusEntity;
import com.example.int221integratedkk1_backend.Entities.TaskEntity;
import com.example.int221integratedkk1_backend.Exception.ItemNotFoundException;
import com.example.int221integratedkk1_backend.Repositories.StatusRepository;
import com.example.int221integratedkk1_backend.Repositories.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
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

    public List<TaskDTO> getAllTasks(List<String> filterStatuses, String sortBy, String sortDirection) {
        List<TaskEntity> tasks;
        Sort.Direction direction = null;

        // Check if sortDirection is valid
        if (sortDirection != null && !sortDirection.trim().isEmpty()) {
            String directionUpper = sortDirection.trim().toUpperCase(); // Convert to upper case
            if ("ASC".equals(directionUpper)) {
                direction = Sort.Direction.ASC;
            } else if ("DESC".equals(directionUpper)) {
                direction = Sort.Direction.DESC;
            } else {
                throw new IllegalArgumentException("Invalid value for sortDirection: " + sortDirection);
            }
        } else {
            // If sortDirection is null or empty, default to ASC
            direction = Sort.Direction.ASC;
        }

        Sort sort = Sort.by(direction, sortBy);

        if (filterStatuses != null && !filterStatuses.isEmpty()) {
            tasks = repository.findAllByStatusNames(filterStatuses,sort);
            LOGGER.info("Filtered tasks by statuses: " + filterStatuses);
        } else {
            tasks = repository.findAll(sort);
            LOGGER.info("Retrieved all tasks sorted by " + sortBy + " in " + sortDirection + " order");
        }
        return listMapper.mapList(tasks, TaskDTO.class);
    }

    public TaskEntity getTaskById(int taskId) {
        return repository.findById(taskId).orElse(null);
    }

    public TaskEntity createTask(TaskRequest task) {
        TaskEntity taskEntity = modelMapper.map(task, TaskEntity.class);
        StatusEntity statusEntity = statusRepository.findById(task.getStatus())
                .orElseThrow(() -> new ItemNotFoundException());
        if (task.getTitle() == null) {
            throw new IllegalArgumentException("Title cannot be null");
        } else
            taskEntity.setStatus(statusEntity);
        return repository.save(taskEntity);
    }

    public TaskEntity updateTask(TaskEntity task) {
        return repository.save(task);
    }

    public void deleteTask(int taskId) {
        repository.deleteById(taskId);
    }

//     // filter
//    public List<TaskEntity> getTasksByStatusesAndSort(List<String> statusNames , Sort sort) {
//        return repository.findByStatusNamesAndSort(statusNames , sort);
//    }
}

