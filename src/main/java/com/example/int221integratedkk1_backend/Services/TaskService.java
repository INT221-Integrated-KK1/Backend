package com.example.int221integratedkk1_backend.Services;

import com.example.int221integratedkk1_backend.DTOS.TaskDTO;
import com.example.int221integratedkk1_backend.Entities.Task;
import com.example.int221integratedkk1_backend.Repositories.TaskRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

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

    public TaskDTO getTaskById(Integer id) {
        Task task = repository.findById(id).orElse(null);
        if (task != null) {
            return listMapper.mapList(List.of(task), TaskDTO.class).get(0);
        } else {
            return null;
        }
    }

    public TaskDTO addTask(TaskDTO taskDTO) {
        Task task = listMapper.mapList(List.of(taskDTO), Task.class).get(0);
        if (task.getTaskStatus() == null || task.getTaskStatus().toString().isEmpty()) {
            task.setTaskStatus("No Status");
        }
        ZonedDateTime currentDateTime = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC);
        task.setCreatedOn(currentDateTime);
        task.setUpdatedOn(currentDateTime);
        Task savedTask = repository.save(task);
        TaskDTO savedTaskDTO = listMapper.mapList(List.of(savedTask), TaskDTO.class).get(0);
        savedTaskDTO.setTaskId(savedTask.getTaskId());
        return listMapper.mapList(List.of(savedTask), TaskDTO.class).get(0);
    }

    public boolean deleteTaskById(Integer id) {
        Optional<Task> taskOptional = repository.findById(id);
        if (taskOptional.isPresent()) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateTask(Integer id, Task updatedTask) {
        Optional<Task> optionalTask = repository.findById(id);
        if (optionalTask.isPresent()) {
            Task existingTask = optionalTask.get();
            existingTask.setTaskTitle(updatedTask.getTaskTitle());
            existingTask.setTaskDescription(updatedTask.getTaskDescription());
            existingTask.setTaskStatus(updatedTask.getTaskStatus());
            repository.save(existingTask);
            return true;
        } else {
            return false;
        }
    }
}
