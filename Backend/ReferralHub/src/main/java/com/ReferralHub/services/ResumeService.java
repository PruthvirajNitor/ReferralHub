package com.ReferralHub.services;

import com.ReferralHub.entities.Candidate;
import com.ReferralHub.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ResumeService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Value("${project.resume}")
    private String path;

    public String uploadFile(MultipartFile file) throws IOException {
        String name = file.getOriginalFilename();
        String filePath = path + name;

        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        Files.copy(file.getInputStream(), Paths.get(filePath));
        return filePath;
    }

    public Resource loadResumeAsResource(Long candidateId) {
        // Retrieve the candidate from the database using candidateId
        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow(() -> new RuntimeException("Candidate not found"));

        // Get the resume file path
        String filePath = candidate.getResume();

        filePath = filePath.replace("\\", "/");

        // Print the file path for debugging
        System.out.println("File path: " + filePath);

        // Create a Path object
        Path path = Paths.get(filePath);

        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException("File not found", e);
        }

        return resource;
    }


}
