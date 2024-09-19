package com.ReferralHub.controllers;

import com.ReferralHub.DTO.*;
import com.ReferralHub.entities.ApiResponse;
import com.ReferralHub.entities.Candidate;
import com.ReferralHub.entities.Job;
import com.ReferralHub.entities.Status;
import com.ReferralHub.services.CandidateService;
import com.ReferralHub.services.JobService;
import com.ReferralHub.services.ResumeService;
//import jakarta.annotation.Resource;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/hr")
@CrossOrigin()
public class HrController {

    @Autowired
    private JobService jobService;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private CandidateService candidateService;

    @GetMapping("/job-list")
    public ResponseEntity<?> getJobsList(){
        return ResponseEntity.ok(jobService.getJobList());
    }

    @GetMapping("/{id}/getJobDetails")
    public ResponseEntity<JobDetailsDTO> getJobDetails(@PathVariable Long id){
        JobDetailsDTO jobDetailsDTO = jobService.getJobDetails(id);
        return ResponseEntity.ok(jobDetailsDTO);
    }

    @PostMapping("/addJob")
    public ResponseEntity<ApiResponse> addNewJob(@ModelAttribute JobDetailsDTO jobDetailsDTO){
        Job job = jobService.addNewJob(jobDetailsDTO);
        if(job != null){
            return ResponseEntity.ok(new ApiResponse("New Job opening created"));
        }else{
            return ResponseEntity.status(500).body(new ApiResponse("Unable to create job opening"));
        }
    }



    @PutMapping("/changeJobStatus")
    public ResponseEntity<ApiResponse> changeJobStatus(@RequestBody JobStatusDTO jobStatusDTO) {
        Job job = jobService.changeStatus(jobStatusDTO);
        return ResponseEntity.ok().body(new ApiResponse("Status changed successfully"));
    }


    @PutMapping("/changeCandidateStatus/{candidateId}/{jobId}")
    public ResponseEntity<ApiResponse> changeCandidateStatus(
            @PathVariable Long candidateId,
            @PathVariable Long jobId,
            @RequestBody Status status) {
        Candidate candidate = candidateService.changeStatusAndCalcBonus(candidateId, jobId, status);

        if (candidate != null) {
            return ResponseEntity.ok(new ApiResponse("Status changed. Bonus calculated"));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Unable to change status and calculate bonus"));
        }
    }

    @GetMapping("/getReferredCandidates/{employeeId}")
    public ResponseEntity<?> getReferredCandidates(@PathVariable Long employeeId){
        List<CandidateDTO> candidateList = candidateService.getReferredCandidates(employeeId);
        return ResponseEntity.ok(candidateList);
    }

    @GetMapping("/downloadCandidateResume/{id}")
    public  ResponseEntity<Resource> downloadCandidateResume(@PathVariable Long id){
        Resource resource = resumeService.loadResumeAsResource(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/getClosedJobs")
    public ResponseEntity<?> getClosedJobs(){
        List<JobListDTO> jobs = jobService.getClosedJobs();
        if(jobs != null){
            return ResponseEntity.ok(jobs);
        }else{
            return ResponseEntity.status(500).body(new ApiResponse("No Closed Jobs"));
        }
    }

    //New Method
//    @GetMapping("/resume/{fileName}")
//    public ResponseEntity<?> getResume(@PathVariable String fileName) {
//        Path filePath = Paths.get("resumes").resolve(fileName).normalize();
//        Resource resource = new FileSystemResource(filePath.toFile());
//        if (resource.exists()) {
//            return ResponseEntity.ok()
//                    .contentType(MediaType.APPLICATION_PDF)
//                    .body(resource);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

}
