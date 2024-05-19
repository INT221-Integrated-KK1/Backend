package com.example.int221integratedkk1_backend.Repositories;

import com.example.int221integratedkk1_backend.Entities.StatusEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<StatusEntity, Integer> {
}
