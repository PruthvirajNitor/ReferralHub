package com.ReferralHub.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Candidate extends BaseEntity{

    private String name;
    private String email;
    private String mobileNum;
    private int experience;

    @Enumerated(EnumType.STRING)
    private Status status;
    private String resume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referred_by")
    private Employee referredBy;

    @ManyToMany
    @JoinTable(
            name = "candidate_job",
            joinColumns = @JoinColumn(name = "candidate_id"),
            inverseJoinColumns = @JoinColumn(name = "job_id")
    )
    private List<Job> appliedJobs = new ArrayList<>();

    public Candidate(){}

    public Candidate(String name, String email, String mobileNum, int experience, Status status, String resume, Employee referredBy) {
        this.name = name;
        this.email = email;
        this.mobileNum = mobileNum;
        this.experience = experience;
        this.status = status;
        this.resume = resume;
        this.referredBy = referredBy;
    }

    public void addJob(Job job){
        appliedJobs.add(job);
        job.getCandidates().add(this);
    }

    public void removeJob(Job job){
        appliedJobs.remove(job);
        job.getCandidates().remove(this);
    }

}
