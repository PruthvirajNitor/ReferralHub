package com.ReferralHub.services;

import com.ReferralHub.DTO.*;
import com.ReferralHub.entities.Candidate;
import com.ReferralHub.entities.Employee;
import com.ReferralHub.entities.Job;
import com.ReferralHub.entities.Status;
import com.ReferralHub.repositories.CandidateRepository;
import com.ReferralHub.repositories.EmployeeRepository;
import com.ReferralHub.repositories.JobRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class CandidateServiceIMPL implements CandidateService {


    @Autowired
    private JobRepository jobRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private CandidateRepository candidateRepo;

    @Autowired
    private EmailService emailService;


    @Override
    public Candidate addNewCandidate(ReferCandidateDTO referCandidateDTO, MultipartFile file) throws IOException{
        Optional<Job> optionalJob =  jobRepo.findById(referCandidateDTO.getJobId());
        Optional<Employee> optionalEmployee = employeeRepo.findById(referCandidateDTO.getReferredById());

        Job job = optionalJob.get();
        Employee employee = optionalEmployee.get();

        Candidate candidate = new Candidate();
        candidate = mapper.map(referCandidateDTO,Candidate.class);

        String resumePath = resumeService.uploadFile(file);

        candidate.setStatus(Status.APPLIED);
        candidate.setResume(resumePath);

        candidate.setReferredBy(employee);
        candidate.addJob(job);




        String message = "Hi "+ employee.getFirstName() + " " + employee.getLastName()+",\n" +
                "\n" +
                "I am pleased to inform you that your referral for the "+ job.getTitle() + " position has been successful. We appreciate your recommendation and will keep you updated on further developments.\n" +
                "\n" +
                "Thank you for your support.\n" +
                "\n" +
                "Best regards,\n" +
                "Talent Acquistion,\n"+
                "Nitor Infotech";

        String subject = "Successful Candidate Referral";

        String email = employee.getEmail();

        emailService.sendSimpleEmail(email,message,subject);

        return candidateRepo.save(candidate);
    }

    @Override
    public Candidate changeStatusAndCalcBonus(Long candidateId, Long jobId,Status status) {
        Optional<Candidate> optionalCandidate = candidateRepo.findById(candidateId);
        Optional<Job> optionalJob = jobRepo.findById(jobId);

        Job job = optionalJob.get();
        Candidate candidate = optionalCandidate.get();
        Employee referredBy = candidate.getReferredBy();
        candidate.setStatus(status);

        if(status == Status.HIRED){

            candidate.getAppliedJobs().clear();
            candidate.getAppliedJobs().add(job);

            Double bonus = job.getSalary() * 0.1;
            referredBy.setBonus(referredBy.getBonus()+bonus);

            String candidateMessage = "Hi "+ candidate.getName() +",\n" +
                    "\n" +
                    "We are excited to inform you that you have been selected for the "+ job.getTitle() + " position, referred by "+ candidate.getReferredBy().getFirstName() +" "+candidate.getReferredBy().getLastName() + ". Congratulations!\n" +
                    "\n" +
                    "We will communicate further details and next steps shortly.\n" +
                    "\n" +
                    "Best regards,\n" +
                    "Talent Acquistion,\n"+
                    "Nitor Infotech";
            String candidateSubject = "Congratulations on Your Selection";
            String candidateEmail = candidate.getEmail();

            emailService.sendSimpleEmail(candidateEmail,candidateMessage,candidateSubject);

            String employeeMessage = "Hi "+ candidate.getReferredBy().getFirstName() + " " + candidate.getReferredBy().getLastName()+",\n" +
                    "\n" +
                    "I am pleased to inform you that your referred candidate, "+ candidate.getName() +" has been selected for the "+ job.getTitle() + " position. As a token of our appreciation, a bonus "+bonus+" has been allotted to you.\n" +
                    "\n" +
                    "Thank you for your valuable referral and support.\n" +
                    "\n" +
                    "Best regards,\n" +
                    "Talent Acquistion,\n"+
                    "Nitor Infotech";
            String employeeSubject = "Successful Candidate Selection and Bonus Allotment";
            String employeeEmail = candidate.getReferredBy().getEmail();

            emailService.sendSimpleEmail(employeeEmail,employeeMessage,employeeSubject);

            employeeRepo.save(referredBy);

        }

        candidateRepo.save(candidate);

        return candidate;

    }

    @Override
    public List<CandidateDTO> getReferredCandidatesByEmployee(Long employeeId) {
        Optional<Employee> optionalEmployee = employeeRepo.findById(employeeId);
        if (!optionalEmployee.isPresent()) {
            return Collections.emptyList(); // Handle employee not found case
        }

        Employee employee = optionalEmployee.get();
        List<Candidate> referredCandidates = employee.getReferredCandidates();

        return referredCandidates.stream().map(candidate -> {
            CandidateDTO candidateDTO = mapper.map(candidate, CandidateDTO.class);
            List<JobTitleDTO> jobTitleDTOS = candidate.getAppliedJobs().stream().map(job -> {
                JobTitleDTO jobTitleDTO = new JobTitleDTO();
                jobTitleDTO.setTitle(job.getTitle());
                return jobTitleDTO;
            }).collect(Collectors.toList());
            candidateDTO.setAppliedJob(jobTitleDTOS);
            return candidateDTO;
        }).collect(Collectors.toList());
    }


    @Override
    public List<CandidateDTO> getReferredCandidates(Long jobId) {
            Optional<Job> optionalJob = jobRepo.findById(jobId);
            Job jobs = optionalJob.get();

            List<Candidate> referredCandidates = jobs.getCandidates();
        System.out.println(referredCandidates);

            return referredCandidates.stream().map(candidate -> {
                CandidateDTO candidateDTO = mapper.map(candidate,CandidateDTO.class);
                List<JobTitleDTO> jobTitleDTOS = candidate.getAppliedJobs().stream().map(job -> {
                    JobTitleDTO jobTitleDTO = new JobTitleDTO();
                    jobTitleDTO.setTitle(job.getTitle());
                    return jobTitleDTO;
                }).collect(Collectors.toList());
                candidateDTO.setAppliedJob(jobTitleDTOS);
                return candidateDTO;
            }).collect(Collectors.toList());

    }

    @Override
    public Candidate referOldCandidate(String email, Long jobId) {
        Optional<Candidate> optionalCandidate = candidateRepo.findByEmail(email);
        Candidate candidate = optionalCandidate.get();

        Optional<Job> optionalJob = jobRepo.findById(jobId);
        Job job = optionalJob.get();

        candidate.getAppliedJobs().add(job);
        job.getCandidates().add(candidate);

        candidateRepo.save(candidate);
        jobRepo.save(job);

        return candidate;
    }
}
