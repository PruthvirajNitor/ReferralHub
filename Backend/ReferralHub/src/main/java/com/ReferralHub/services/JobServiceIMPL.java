package com.ReferralHub.services;

import com.ReferralHub.DTO.EmployeeListDTO;
import com.ReferralHub.DTO.JobDetailsDTO;
import com.ReferralHub.DTO.JobListDTO;
import com.ReferralHub.DTO.JobStatusDTO;
import com.ReferralHub.entities.Job;
import com.ReferralHub.repositories.JobRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class JobServiceIMPL implements JobService{

    @Autowired
    private JobRepository jobRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<JobListDTO> getJobList() {
        List<Job> jobs = jobRepo.findByStatus(true);
        return jobs.stream().map(job -> mapper.map(job, JobListDTO.class)).collect(Collectors.toList());
    }

    @Override
    public JobDetailsDTO getJobDetails(Long id) {
        Optional<Job> optionalJob = jobRepo.findById(id);
        Job job = optionalJob.get();
        return mapper.map(job,JobDetailsDTO.class);
    }

    @Override
    public Job addNewJob(JobDetailsDTO jobDetailsDTO) {
        Job job = mapper.map(jobDetailsDTO, Job.class);
        job.setComment("");
        job.setStatus(true);
        return jobRepo.save(job);
    }

    @Override
    public Job changeStatus(JobStatusDTO jobStatusDTO) {
        Optional<Job> optionalJob = jobRepo.findById(jobStatusDTO.getId());
        if (optionalJob.isPresent()) {
            Job job = optionalJob.get();
            job.setStatus(false); // Assuming 'false' means closed
            job.setComment(jobStatusDTO.getComment());
            jobRepo.save(job); // Save the updated job
            return job;
        } else {
            throw new EntityNotFoundException("Job not found with id " + jobStatusDTO.getId());
        }
    }

    @Override
    public Job getJobByTitle(String jobTitle) {
        Job job = jobRepo.findByTitle(jobTitle);
        return job;
    }

    @Override
    public List<JobListDTO> getClosedJobs() {
        List<Job> jobs = jobRepo.findByStatus(false);
        return jobs.stream().map(job -> mapper.map(job,JobListDTO.class)).collect(Collectors.toList());
    }
}
