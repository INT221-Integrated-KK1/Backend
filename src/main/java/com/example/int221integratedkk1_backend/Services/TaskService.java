package com.example.int221integratedkk1_backend.Services;

import com.example.int221integratedkk1_backend.DTOS.TaskDTO;
import com.example.int221integratedkk1_backend.Entities.Task;
import com.example.int221integratedkk1_backend.Repositories.TaskRepository;
import org.springframework.stereotype.Service;
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
        List<Task> tasks = repository.findAll();
        return listMapper.mapList(tasks, TaskDTO.class);
    }

    public TaskDTO getTaskById(Integer id) {
        Task task = repository.findById(id).orElse(null);
        if (task != null) {
            return listMapper.mapList(List.of(task), TaskDTO.class).get(0);
        } else {
            return null;
        }
    }
}
