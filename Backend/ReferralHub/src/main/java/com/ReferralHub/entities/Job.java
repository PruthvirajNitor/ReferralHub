package com.ReferralHub.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
public class Job extends BaseEntity{

    private String title;
    private String description;
    private Double salary;
    private String location;

    @Convert(converter = StringArrayConverter.class)
    private String[] skillsRequired;
    private int experienceRequired;
    private int numOpenings;
    private boolean status;
    private String comment;


    @ManyToMany(mappedBy = "appliedJobs")
    private List<Candidate> candidates = new ArrayList<>();

    public Job(){}

    public Job(Long id, String title, String description, Double salary, String location, String[] skillsRequired, int experienceRequired, int numOpenings, List<Candidate> candidates,String comment) {
        super(id);
        this.title = title;
        this.description = description;
        this.salary = salary;
        this.location = location;
        this.skillsRequired = skillsRequired;
        this.experienceRequired = experienceRequired;
        this.numOpenings = numOpenings;
        this.candidates = candidates;
    }

    public void addCandidate(Candidate candidate){
        candidates.add(candidate);
        candidate.getAppliedJobs().add(this);
    }
    public void removeCandidate(Candidate candidate){
        candidates.remove(candidate);
        candidate.getAppliedJobs().remove(this);
    }

}
