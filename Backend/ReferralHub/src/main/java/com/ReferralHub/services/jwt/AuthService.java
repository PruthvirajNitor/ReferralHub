package com.ReferralHub.services.jwt;

import com.ReferralHub.DTO.EmployeeDTO;
import com.ReferralHub.entities.Employee;
import com.ReferralHub.entities.Role;
import com.ReferralHub.repositories.EmployeeRepository;
import com.ReferralHub.services.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AuthService {

    @Autowired
    private final EmployeeRepository employeeRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final ImageService imageService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    public AuthService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, ImageService imageService) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.imageService = imageService;
    }

    public boolean createUser(EmployeeDTO signupRequest, MultipartFile file) throws IOException {
        if(employeeRepository.existsByEmail(signupRequest.getEmail())) {
            return false;
        }
        String imgPath = imageService.uploadFile(file);
        Employee employee=new Employee();
        //mapper.map(signupRequest,Employee.class);
        BeanUtils.copyProperties(signupRequest,employee);
        employee.setRole(signupRequest.getRole());

         String hashPassword=passwordEncoder.encode(signupRequest.getPassword());
         employee.setPassword(hashPassword);
         employee.setStatus(false);
        employee.setImagePath(imgPath);
        employee.setBonus(0.0);
        employeeRepository.save(employee);
        return true;
    }
}
