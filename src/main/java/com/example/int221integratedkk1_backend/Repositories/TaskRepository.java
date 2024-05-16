package com.example.int221integratedkk1_backend.Repositories;

import com.example.int221integratedkk1_backend.Entities.StatusEntity;
import com.example.int221integratedkk1_backend.Entities.TaskEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {

    List<TaskEntity> findByStatusId(int statusId);

    @Query("SELECT t FROM TaskEntity t JOIN t.status s WHERE s.name IN :statusNames")
    List<TaskEntity> findByStatusNamesAndSort(@Param("statusNames") List<String> statusNames);
}



