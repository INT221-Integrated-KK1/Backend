package com.example.int221integratedkk1_backend.Controllers.Account;

import com.example.int221integratedkk1_backend.DTOS.JwtRequestUser;
import com.example.int221integratedkk1_backend.DTOS.JwtResponseToken;
import com.example.int221integratedkk1_backend.Entities.Account.UsersEntity;
import com.example.int221integratedkk1_backend.Exception.ResponseMessage;
import com.example.int221integratedkk1_backend.Exception.UnauthorizedException;
import com.example.int221integratedkk1_backend.Exception.ValidateInputException;
import com.example.int221integratedkk1_backend.Services.Account.JwtTokenUtil;
import com.example.int221integratedkk1_backend.Services.Account.JwtUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;


@RestController
public class AuthenticationController {
    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public List<UsersEntity> getAllUsers(){
        return jwtUserDetailsService.getAllUser();
    }

//    @PostMapping("/login")
//    public ResponseEntity<Object> login(@Valid  @RequestBody JwtRequestUser user) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            String token = jwtTokenUtil.generateToken(userDetails);
//            return ResponseEntity.ok(new JwtResponseToken(token));
//        } catch (UnauthorizedException e) {
//            throw new UnauthorizedException("Username or Password is incorrect.");
//        } catch (Exception e) {
//            throw new UnauthorizedException("Username or Password is incorrect.");
//        }
//    }
//
//    @GetMapping("/validate-token")
//    public ResponseEntity<Object> validateToken(@RequestHeader("Authorization") String requestTokenHeader) {
//        Claims claims = null;
//        String jwtToken = null;
//        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
//            jwtToken = requestTokenHeader.substring(7);
//            try {
//                claims = jwtTokenUtil.getAllClaimsFromToken(jwtToken); }
//            catch (IllegalArgumentException e) {
//                System.out.println("Unable to get JWT Token"); }
//            catch (ExpiredJwtException e) {
//                System.out.println("JWT Token has expired");
//            }
//        } else {
//            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED,
//                    "JWT Token does not begin with Bearer String"); }
//        return ResponseEntity.ok(claims);
//    }


    @PostMapping("/login")
    public ResponseEntity<ResponseMessage> login(@Valid @RequestBody JwtRequestUser user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtTokenUtil.generateToken(userDetails);
            ResponseMessage response = new ResponseMessage("Login Successful");
            response.setToken(token); // Assuming you have a token field in ResponseMessage
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseMessage("Username or Password is incorrect."));
        }
    }

    @GetMapping("/validate-token")
    public ResponseEntity<ResponseMessage> validateToken(@RequestHeader("Authorization") String requestTokenHeader) {
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            String jwtToken = requestTokenHeader.substring(7);
            try {
                Claims claims = jwtTokenUtil.getAllClaimsFromToken(jwtToken);
                ResponseMessage response = new ResponseMessage("Token is valid");
                response.setClaims(claims); // Assuming you have a claims field in ResponseMessage
                return ResponseEntity.ok(response);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseMessage("Unable to get JWT Token"));
            } catch (ExpiredJwtException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseMessage("JWT Token has expired"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage("JWT Token does not begin with Bearer String"));
        }
    }
}

