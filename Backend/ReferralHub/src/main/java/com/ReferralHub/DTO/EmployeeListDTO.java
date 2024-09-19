package com.ReferralHub.DTO;

import com.ReferralHub.entities.Department;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
public class EmployeeListDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String imagePath;
    private Department department;


}
