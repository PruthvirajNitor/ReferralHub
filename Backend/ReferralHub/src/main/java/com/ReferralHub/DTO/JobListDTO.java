package com.ReferralHub.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobListDTO {

    private Long id;
    private String title;
    private Double salary;
    private String location;
    private int numOpenings;

}
