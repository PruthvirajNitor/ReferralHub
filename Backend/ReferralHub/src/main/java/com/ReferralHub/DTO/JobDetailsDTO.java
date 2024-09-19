package com.ReferralHub.DTO;

import com.ReferralHub.entities.Candidate;
import com.ReferralHub.entities.StringArrayConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class JobDetailsDTO {

    private String title;
    private String description;
    private Double salary;
    private String location;
    private String[] skillsRequired;
    private int experienceRequired;
    private int numOpenings;
    private String comment;

}
