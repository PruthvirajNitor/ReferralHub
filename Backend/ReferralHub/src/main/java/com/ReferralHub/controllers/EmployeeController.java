package com.ReferralHub.controllers;

import com.ReferralHub.DTO.*;
import com.ReferralHub.entities.ApiResponse;
import com.ReferralHub.entities.Candidate;
import com.ReferralHub.entities.Employee;
import com.ReferralHub.entities.Job;
import com.ReferralHub.repositories.EmployeeRepository;
import com.ReferralHub.repositories.JobRepository;
import com.ReferralHub.services.CandidateService;
import com.ReferralHub.services.EmployeeService;
import com.ReferralHub.services.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/employee")
@CrossOrigin()
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JobService jobService;

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

    @PostMapping(value = "/referNewCandidate",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> referNewCandidate(@ModelAttribute ReferCandidateDTO referCandidateDTO, @RequestPart MultipartFile file) throws IOException{

        Candidate candidate = candidateService.addNewCandidate(referCandidateDTO,file);

        if(candidate != null){
            return ResponseEntity.ok(new ApiResponse("New Candidate succesfully created"));
        }else {
            return ResponseEntity.status(500).body(new ApiResponse("Unable to create new Candidate"));
        }
    }

    @PutMapping("/referOldCandidate")
    public ResponseEntity<ApiResponse> referOldCandidate(@RequestBody String email,@PathVariable Long jobId){
        Candidate candidate = candidateService.referOldCandidate(email,jobId);

        if(candidate != null){
            return ResponseEntity.ok(new ApiResponse("Candidate referred succesfully"));
        }else{
            return ResponseEntity.status(500).body(new ApiResponse("Unable to refer candidate"));
        }
    }

    @GetMapping("/getJobByTitle/{jobTitle}")
    public ResponseEntity<Job> getJobByTitle(@PathVariable String jobTitle){
        Job job = jobService.getJobByTitle(jobTitle);
        return ResponseEntity.ok(job);
    }

    @GetMapping("/getReferredCandidates/{employeeId}")
    public ResponseEntity<?> getReferredCandidatesByEmployee(@PathVariable Long employeeId){
        List<CandidateDTO> candidateList = candidateService.getReferredCandidatesByEmployee(employeeId);
        return ResponseEntity.ok(candidateList);
    }

    @GetMapping("/getName/{employeeId}")
    public ResponseEntity<?> getNameById(@PathVariable Long employeeId)
    {
        ProfileDTO profileDTO = employeeService.getEmployeeNameById(employeeId);
        if(profileDTO != null)
            return ResponseEntity.ok(profileDTO);
        else
            return ResponseEntity.status(500).body(new ApiResponse("Unable to find employee"));
    }

}
