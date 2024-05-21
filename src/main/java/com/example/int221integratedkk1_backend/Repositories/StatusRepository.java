package com.example.int221integratedkk1_backend.Repositories;

import com.example.int221integratedkk1_backend.Entities.StatusEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<StatusEntity, Integer> {
    Optional<StatusEntity> findByName(String name);
}
