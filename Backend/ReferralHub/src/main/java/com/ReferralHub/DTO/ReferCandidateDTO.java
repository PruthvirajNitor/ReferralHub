package com.ReferralHub.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReferCandidateDTO {

    private String name;
    private String email;
    private String mobileNum;
    private int experience;
    private Long jobId;
    private Long referredById;
}
