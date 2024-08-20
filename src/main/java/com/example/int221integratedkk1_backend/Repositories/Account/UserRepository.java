package com.example.int221integratedkk1_backend.Repositories.Account;

import com.example.int221integratedkk1_backend.Entities.Account.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UsersEntity, String> {
    Optional<UsersEntity> findByUsername(String username);
}
