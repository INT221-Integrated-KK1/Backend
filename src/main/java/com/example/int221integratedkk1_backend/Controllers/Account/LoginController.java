package com.example.int221integratedkk1_backend.Controllers.Account;

import com.example.int221integratedkk1_backend.DTOS.LoginReqDTO;
import com.example.int221integratedkk1_backend.Services.Account.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = {"http://localhost:5173", "https://ip23kk1.sit.kmutt.ac.th", "https://intproj23.sit.kmutt.ac.th", "https://intproj23.sit.kmutt.ac.th:8080", "https://ip23kk1.sit.kmutt.ac.th:8080"})
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> login(@Validated @RequestBody LoginReqDTO loginRequest) {
        boolean isAuthenticated = loginService.authenticate(loginRequest.getUserName(), loginRequest.getPassword());

        Map<String, String> response = new HashMap<>();

        if (isAuthenticated) {
            response.put("status", "success");
            response.put("detail", "Login Successful");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", "Username or Password is incorrect");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
