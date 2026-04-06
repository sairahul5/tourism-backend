package com.tourism.controller;

import com.tourism.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin("*")
public class FileUploadController {

    @Autowired
    private FileStorageService storageService;

    @PostMapping
    public Map<String, String> uploadFile(@RequestParam("file") MultipartFile file) {
        String url = storageService.saveFile(file);
        Map<String, String> response = new HashMap<>();
        response.put("url", url);
        return response;
    }
}
