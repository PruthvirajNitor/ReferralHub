package com.ReferralHub.DTO;

import com.ReferralHub.entities.Department;
import com.ReferralHub.entities.Role;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmployeeDTO {

    private String firstName;
    private String lastName;
    private String email;
    private Double salary;
    private Double bonus;
    private String mobileNum;
    private Department department;
    private Role role;
    private String password;

}
