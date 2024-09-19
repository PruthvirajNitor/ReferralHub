package com.ReferralHub.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class ImageService {

    @Value("${project.image}")
    private String path;

    public String uploadFile(MultipartFile file) throws IOException {


        String name = file.getOriginalFilename();
        String filePath=path+name;

        File f = new File(path);

        if(!f.exists()){
            f.mkdir();
        }

        Files.copy(file.getInputStream(), Paths.get(filePath));
        return filePath;

    }
}
