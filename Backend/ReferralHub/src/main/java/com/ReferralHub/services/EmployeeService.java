package com.ReferralHub.services;


import com.ReferralHub.DTO.EmployeeDTO;
import com.ReferralHub.DTO.EmployeeListDTO;
import com.ReferralHub.DTO.ProfileDTO;
import com.ReferralHub.DTO.SignInResponse;
import com.ReferralHub.entities.Employee;
import com.ReferralHub.entities.Role;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface EmployeeService {

    List<EmployeeListDTO> getApprovedEmp();

    List<EmployeeListDTO> getPendingEmp();

    Employee changeEmpStatus(Long id);
    EmployeeListDTO findByEmail(String email);
    //EmployeeListDTO registerEmployee(EmployeeAddDTO dto);

    //SignInResponse loginEmp(String emailId, String password, Role role);

    //Employee addNewEmployee (EmployeeDTO employeeDTO, MultipartFile file) throws IOException;

    void deleteEmployee(Long id);

    Employee getEmployeeName(String name);

    ProfileDTO getEmployeeNameById(Long id);
}
