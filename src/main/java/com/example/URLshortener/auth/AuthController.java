package com.example.URLshortener.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.URLshortener.auth.dto.JwtResponse;
import com.example.URLshortener.auth.dto.LoginRequest;
import com.example.URLshortener.auth.dto.RegisterRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginRequest(@Valid @RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.loginExistingUser(request));
    }

    @PostMapping("/signup")
    public ResponseEntity<JwtResponse> signupRequest(@Valid @RequestBody RegisterRequest request){
        JwtResponse response = authService.registerNewUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
}
