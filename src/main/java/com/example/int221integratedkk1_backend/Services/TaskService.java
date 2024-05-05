package com.example.int221integratedkk1_backend.Services;

import com.example.int221integratedkk1_backend.DTOS.AddDTO;
import com.example.int221integratedkk1_backend.DTOS.EditDTO;
import com.example.int221integratedkk1_backend.DTOS.TaskDTO;
import com.example.int221integratedkk1_backend.Entities.Task;
import com.example.int221integratedkk1_backend.Exception.ItemNotFoundException;
import com.example.int221integratedkk1_backend.Repositories.TaskRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Setter
@Getter
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
    public Task getTaskById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Task id " + id + " does not exist !!!"));
    }
    public TaskDTO addTask(AddDTO addTaskDTO) {
        Task task = listMapper.mapList(List.of(addTaskDTO), Task.class).get(0);
        if (task.getTaskStatus() == null || task.getTaskStatus().toString().isEmpty()) {
            task.setTaskStatus("No Status");
        }
        task.setCreatedOn(null);
        task.setUpdatedOn(null);
        Task savedTask = repository.save(task);
        TaskDTO savedTaskDTO = listMapper.mapList(List.of(savedTask), TaskDTO.class).get(0);
        savedTaskDTO.setTaskId(savedTask.getTaskId());
        return savedTaskDTO;
    }

    public boolean deleteTaskById(Integer id) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("NOT FOUND"));
        repository.delete(task);
        return true;
    }

    public boolean updateTask(Integer id, EditDTO editTaskDTO) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("NOT FOUND"));
        task.setTaskTitle(editTaskDTO.getTaskTitle());
        task.setTaskDescription(editTaskDTO.getTaskDescription());
        task.setTaskAssignees(editTaskDTO.getTaskAssignees());
        task.setTaskStatus(editTaskDTO.getTaskStatus());
        // ZonedDateTime now = ZonedDateTime.now();
        // task.setUpdatedOn(ZonedDateTime.now());
        repository.save(task);
        return true;
    }

}

