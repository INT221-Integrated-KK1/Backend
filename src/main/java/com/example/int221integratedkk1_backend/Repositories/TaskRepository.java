package com.example.int221integratedkk1_backend.Repositories;

import com.example.int221integratedkk1_backend.Entities.StatusEntity;
import com.example.int221integratedkk1_backend.Entities.TaskEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {


    List<TaskEntity> findByStatus(StatusEntity status);

    // Method to save a list of tasks


    @Query("SELECT t FROM TaskEntity t JOIN FETCH t.status") // Ensure status is fetched
    List<TaskEntity> findAllWithStatus();

}



