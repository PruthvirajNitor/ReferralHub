package com.ReferralHub.services;

import com.ReferralHub.DTO.JobDetailsDTO;
import com.ReferralHub.DTO.JobListDTO;
import com.ReferralHub.DTO.JobStatusDTO;
import com.ReferralHub.entities.Job;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JobService {

    List<JobListDTO> getJobList();

    JobDetailsDTO getJobDetails(Long id);

    Job addNewJob(JobDetailsDTO jobDetailsDTO);

    Job changeStatus(JobStatusDTO jobStatusDTO);

    Job getJobByTitle(String jobTitle);

    List<JobListDTO> getClosedJobs();
}
