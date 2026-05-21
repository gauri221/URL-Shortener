package com.example.URLshortener.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.URLshortener.auth.dto.JwtResponse;
import com.example.URLshortener.auth.dto.LoginRequest;
import com.example.URLshortener.auth.dto.RegisterRequest;
import com.example.URLshortener.exceptions.UserAlreadyExistsException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public JwtResponse registerNewUser(RegisterRequest request){
        if (userRepository.existsByEmail(request.getEmail())){
            throw new UserAlreadyExistsException("user with this email already exists " + request.getEmail());
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User();

        user.setPassword(hashedPassword);
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        userRepository.save(user);

        JwtResponse response = new JwtResponse();
        String token=jwtUtil.generateToken(request.getEmail());

        response.setEmail(request.getEmail());
        response.setName(request.getName());
        response.setToken(token);

        return response;
    }

    public JwtResponse loginExistingUser(LoginRequest request){

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()
            )
        );

        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(
                () -> new UsernameNotFoundException("No user found with this email " + request.getEmail())
        );
        
        JwtResponse response = new JwtResponse();
        String token=jwtUtil.generateToken(request.getEmail());

        response.setEmail(request.getEmail());
        response.setName(user.getName());
        response.setToken(token);

        return response;
    }
}
