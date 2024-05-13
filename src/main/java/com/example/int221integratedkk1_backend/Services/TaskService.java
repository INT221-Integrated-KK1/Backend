package com.example.int221integratedkk1_backend.Services;

import com.example.int221integratedkk1_backend.DTOS.TaskDTO;
import com.example.int221integratedkk1_backend.Entities.TaskEntity;
import com.example.int221integratedkk1_backend.Exception.ItemNotFoundException;
import com.example.int221integratedkk1_backend.Repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository repository;
    private final ListMapper listMapper;
    public TaskService(TaskRepository repository, ListMapper listMapper) {
        this.repository = repository;
        this.listMapper = listMapper;
    }

    public List<TaskDTO> getAllTasks() {
        List<TaskEntity> tasks = repository.findAll();
        List<TaskDTO> taskDTOs = listMapper.mapList(tasks, TaskDTO.class);

        return taskDTOs;
    }
    public TaskEntity getTaskById(Integer id) {
        TaskEntity task = repository.findById(id).orElseThrow(() -> new ItemNotFoundException("Task id " + id + " does not exist !!!"));
        task.setStatus(task.getStatus());
        return task;
    }
    public TaskEntity createTask(TaskEntity task) {

        if (task.getTitle() == null) {
            throw new IllegalArgumentException("Title cannot be null");
        } else
        return repository.save(task);
    }
  
    public boolean updateTask(Integer id,TaskEntity task) {
        TaskEntity taskEntity = repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("NOT FOUND"));
       taskEntity.setTitle(task.getTitle().trim());
       taskEntity.setDescription(task.getDescription().trim());
       taskEntity.setAssignees(task.getAssignees().trim());
        taskEntity.setStatus(task.getStatus());
        // ZonedDateTime now = ZonedDateTime.now();
        // task.setUpdatedOn(ZonedDateTime.now());
        taskEntity.setUpdatedOn(OffsetDateTime.now());

        repository.save(task);
        return true;
    }

    public boolean deleteTaskById(Integer id) {
        TaskEntity task = repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("NOT FOUND"));
        repository.delete(task);
        return true;
    }
}

