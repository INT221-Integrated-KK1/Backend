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
//    public List<TaskDTO> getAllTasks() {
//        List<Task> tasks = repository.findAll();
//        return listMapper.mapList(tasks, TaskDTO.class);
//    }


    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = repository.findAll();
        List<TaskDTO> taskDTOs = listMapper.mapList(tasks, TaskDTO.class);

        // Modify status field in each TaskDTO
        for (TaskDTO taskDTO : taskDTOs) {
            taskDTO.setStatus(convertToEnumFormat((String) taskDTO.getStatus()));
        }

        return taskDTOs;
    }

    private String convertToEnumFormat(String status) {
        return status.toUpperCase().replace(" ", "_");
    }
    public Task getTaskById(Integer id) {
        Task task = repository.findById(id).orElseThrow(() -> new ItemNotFoundException("Task id " + id + " does not exist !!!"));
              task.setStatus(convertToEnumFormat(task.getStatus().toString()));
    return task;
    }
    public TaskDTO addTask(AddDTO addTaskDTO) {
        // Check if the title is null
        if (addTaskDTO.getTitle() == null) {
            throw new IllegalArgumentException("Title cannot be null");
        }


        addTaskDTO.setTitle(addTaskDTO.getTitle().trim());

        if (addTaskDTO.getDescription() != null) {
            addTaskDTO.setDescription(addTaskDTO.getDescription().trim());
        }
        if (addTaskDTO.getAssignees() != null) {
            addTaskDTO.setAssignees(addTaskDTO.getAssignees().trim());
        }


        Task task = listMapper.mapList(List.of(addTaskDTO), Task.class).get(0);
        if (task.getStatus() == null || task.getStatus().toString().isEmpty()) {
            task.setStatus("No Status");
        }

        Task savedTask = repository.save(task);
        TaskDTO savedTaskDTO = listMapper.mapList(List.of(savedTask), TaskDTO.class).get(0);
        savedTaskDTO.setId(savedTask.getId());
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
        task.setTitle(editTaskDTO.getTitle());
        task.setDescription(editTaskDTO.getDescription());
        task.setAssignees(editTaskDTO.getAssignees());
        task.setStatus(editTaskDTO.getStatus());
        // ZonedDateTime now = ZonedDateTime.now();
        // task.setUpdatedOn(ZonedDateTime.now());
        repository.save(task);
        return true;
    }

}

