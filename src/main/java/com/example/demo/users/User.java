package com.example.demo.users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class User {

    @CrossOrigin
    @GetMapping("/user")
    public String sendString(){
        return "{\"name\": \"petr\"}";
    }

    @CrossOrigin
    @PostMapping(path = "/users", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Manager> getNumber(@RequestBody Manager manager){

            System.out.println(String.format("It's ==  %s  === from front", manager.toString()));
    
        return new ResponseEntity<>(manager, HttpStatus.CREATED);
    }

}
