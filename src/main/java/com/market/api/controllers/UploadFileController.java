package com.market.api.controllers;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.market.api.controllers.dto.Manager;

@RestController
@RequestMapping(path = "/api")
public class UploadFileController {
    
    @CrossOrigin
    @PostMapping("/upload")
    public ResponseEntity<Manager> uploadFile(@RequestParam("photo") MultipartFile file){
        System.out.println("newfile " + file.getOriginalFilename());
        Path filePath = Paths.get("/home/ivan/projects/market/src/main/resources", file.getOriginalFilename());
        
        try {
            file.transferTo(filePath.toFile());
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        Manager a = new Manager();
        a.setName("petr");
        return ResponseEntity.ok(a);
    }
}
