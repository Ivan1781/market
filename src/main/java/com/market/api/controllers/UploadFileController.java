package com.market.api.controllers;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.market.api.controllers.dto.PhotoResponseDto;
import com.market.api.models.Photo;
import com.market.api.models.UserEntity;
import com.market.api.repositories.PhotoRepository;
import com.market.api.repositories.UserRepository;

@RestController
@RequestMapping(path = "/api")
public class UploadFileController {
    
    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private UserRepository userRepository;

    @CrossOrigin
    @PostMapping("/upload")
    public ResponseEntity<PhotoResponseDto> uploadFile(@RequestParam("photo") MultipartFile file){
        System.out.println("newfile " + file.getOriginalFilename());
        Path filePath = Paths.get("/home/ivan/projects/market/src/main/resources", file.getOriginalFilename());
        try {
            file.transferTo(filePath.toFile());
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Photo photo = new Photo();
        UserEntity user = userRepository.findByUserName(userName).get();
        photo.setPhotoName(file.getOriginalFilename());
        photo.setUserId(user.getId());
        photoRepository.save(photo);
        PhotoResponseDto photoResponseDto = new PhotoResponseDto();
        photoResponseDto.setName(photo.getPhotoName());
        return new ResponseEntity<PhotoResponseDto>(photoResponseDto, HttpStatus.OK);
    }
}
