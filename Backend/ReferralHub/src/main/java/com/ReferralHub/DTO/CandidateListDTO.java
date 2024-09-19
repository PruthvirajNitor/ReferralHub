package com.ReferralHub.DTO;

import com.ReferralHub.entities.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateListDTO {

    private String name;
    private String email;
    private JobTitleDTO appliedJob;
    private Status status;

}
