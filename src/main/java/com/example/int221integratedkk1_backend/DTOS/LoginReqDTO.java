package com.example.int221integratedkk1_backend.DTOS;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReqDTO {
    @NotEmpty(message = "Username must not be empty")
    @Size(max = 50, message = "Username must not exceed 50 characters")
    private String username;

    @NotEmpty(message = "Password must not be empty")
    @Size(max = 14, message = "Password must not exceed 14 characters")
    private String password;
}
