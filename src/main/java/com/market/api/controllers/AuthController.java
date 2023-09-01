package com.market.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.market.api.controllers.dto.RegisterDto;
import com.market.api.models.Role;
import com.market.api.models.UserEntity;
import com.market.api.repositories.RoleRepository;
import com.market.api.repositories.UserRepository;

import java.util.Collections;

import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @PostMapping(value="register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername())){
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }    
        UserEntity user = new UserEntity();
        user.setUserName(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword())); 
        
        Role roles = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(roles));
        
        
        return new ResponseEntity<String>("User successfuly registered", HttpStatus.OK);
    }
    
}
