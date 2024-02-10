package com.moiso.myaws.controller;

import java.net.URL;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.moiso.myaws.service.S3Service;

@RestController("/s3")
public class S3Controller {
    private final S3Service s3Service;

    @Autowired
    public S3Controller(S3Service s3Service){
        this.s3Service = s3Service;
    }

    @GetMapping("/")
    public String getHello(){
        return "Hello to my app";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestBody MultipartFile file) {
        try {
            s3Service.uploadFile(file);
            return "File uploaded successfully!";
        } catch (Exception e) {
            return "Error uploading file: " + e.getMessage();
        }
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity<String>  getPictre(@PathVariable String filename){
        URL signedUrl = s3Service.getPreSignedUrl(filename);
        return ResponseEntity.ok().body(signedUrl.toString());
    }

}
