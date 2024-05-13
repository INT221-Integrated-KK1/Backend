package com.example.int221integratedkk1_backend.Services;

import com.example.int221integratedkk1_backend.DTOS.TaskDTO;
import com.example.int221integratedkk1_backend.Entities.TaskEntity;
import com.example.int221integratedkk1_backend.Repositories.TaskRepository;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

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

    public TaskEntity getTaskById(int taskId) {
        return repository.findById(taskId).orElse(null);
    }
    public TaskEntity createTask(TaskEntity task) {
        task.setTitle(task.getTitle().trim());
        task.setDescription(task.getDescription().trim());
        task.setAssignees(task.getAssignees().trim());

        return repository.save(task);
    }

    public TaskEntity updateTask(TaskEntity task) {
        task.setTitle(task.getTitle().trim());
        task.setDescription(task.getDescription().trim());
        task.setAssignees(task.getAssignees().trim());

        return repository.save(task);
    }
    public void deleteTask(int taskId) {
        repository.deleteById(taskId);
    }
}

