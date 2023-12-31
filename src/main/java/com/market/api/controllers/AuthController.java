package com.market.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.market.api.controllers.dto.AuthResponseDTO;
import com.market.api.controllers.dto.LoginDto;
import com.market.api.controllers.dto.RegisterDto;
import com.market.api.controllers.dto.RegisterResponse;
import com.market.api.models.Role;
import com.market.api.models.UserEntity;
import com.market.api.repositories.RoleRepository;
import com.market.api.repositories.UserRepository;
import com.market.api.security.JWTGenerator;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTGenerator jwtGenerator;
    
    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterDto registerDto) {
        if(userRepository.existsByUserName(registerDto.getUserName())){
            RegisterResponse errorUserExist = new RegisterResponse();
            errorUserExist.setName("User already exists");
            return new ResponseEntity<RegisterResponse>(errorUserExist, HttpStatus.BAD_REQUEST);
        }    
        UserEntity user = new UserEntity();
        user.setUserName(registerDto.getUserName());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword())); 
        Role roles = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(roles));
        userRepository.save(user);
        RegisterResponse successResponse = new RegisterResponse();
        successResponse.setName("User successfuly registered");
        return new ResponseEntity<RegisterResponse>(successResponse, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(value="/login", produces = "application/json")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDto loginDto){   
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        new UsernamePasswordAuthenticationToken(loginDto.getUsername(),
            loginDto.getPassword());
            System.out.println(loginDto.getPassword() + loginDto.getUsername());
            System.out.println(usernamePasswordAuthenticationToken);
        Authentication authentication = authenticationManager.authenticate(
            usernamePasswordAuthenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);
            ResponseEntity<AuthResponseDTO> response = new ResponseEntity<AuthResponseDTO>(new AuthResponseDTO(token), 
            HttpStatus.OK);
            System.out.println(response.toString());
            return response;   
        }
}
