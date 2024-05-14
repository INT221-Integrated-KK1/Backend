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
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    private TaskRepository repository;
    @Autowired
    private ListMapper listMapper;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StatusRepository statusRepository;

    public List<TaskDTO> getAllTasks() {
        List<TaskEntity> tasks = repository.findAll();
        List<TaskDTO> taskDTOs = listMapper.mapList(tasks, TaskDTO.class);
        return taskDTOs;
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
}

