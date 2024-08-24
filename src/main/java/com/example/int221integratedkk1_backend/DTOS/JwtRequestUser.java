package com.example.int221integratedkk1_backend.DTOS;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class JwtRequestUser {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
