package com.ReferralHub.DTO;

import com.ReferralHub.entities.Employee;
import com.ReferralHub.entities.Job;
import com.ReferralHub.entities.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CandidateDTO {

//    private Long id;
//    private String name;
//    private String email;
//    private List<JobTitleDTO> appliedJob;
//    private Status status;
//    private String resume;
//    private Employee referredBy;

    private Long id;
    private String name;
    private String email;
    private List<JobTitleDTO> appliedJob;
    private Status status;


}
