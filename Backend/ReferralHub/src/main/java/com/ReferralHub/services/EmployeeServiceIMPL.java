package com.ReferralHub.services;


import com.ReferralHub.DTO.EmployeeDTO;
import com.ReferralHub.DTO.EmployeeListDTO;
import com.ReferralHub.DTO.ProfileDTO;
import com.ReferralHub.DTO.SignInResponse;
import com.ReferralHub.customException.CustomException;
import com.ReferralHub.entities.Employee;
import com.ReferralHub.entities.Role;
import com.ReferralHub.repositories.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ReferralHub.entities.Department.HR;

@Service
@Transactional
public class EmployeeServiceIMPL implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ImageService imageService;


    @Override
    public List<EmployeeListDTO> getApprovedEmp() {
        System.out.println("API hit");
        List<Employee> employees = employeeRepo.findByStatus(true);
        System.out.println("Got list of approved employees");
        for (Employee employee : employees) {
            System.out.println(employee);
            System.out.println("-------------------------------------------------");
        }
        return employees.stream().map(emp -> mapper.map(emp, EmployeeListDTO.class)).collect(Collectors.toList());

    }

    @Override
    public List<EmployeeListDTO> getPendingEmp() {

        List<Employee> employees = employeeRepo.findByStatus(false);

        return employees.stream().map(emp -> mapper.map(emp,EmployeeListDTO.class)).collect(Collectors.toList());
    }

    @Override
    public Employee changeEmpStatus(Long id) {

        Optional<Employee> optionalEmployee = employeeRepo.findById(id);
        Employee employee = optionalEmployee.get();
        employee.setStatus(true);
        return employeeRepo.save(employee);
    }

    @Override
    public EmployeeListDTO findByEmail(String email){
    Employee emp=employeeRepo.findByEmail(email).orElseThrow(()->new CustomException("User with email not exits"));
    return  mapper.map(emp, EmployeeListDTO.class) ;
}
    @Override
    public void deleteEmployee(Long id) {
        employeeRepo.deleteById(id);
    }

    @Override
    public Employee getEmployeeName(String name) {
        Employee employee = employeeRepo.findByFullName(name);
        return employee;
    }

    @Override
    public ProfileDTO getEmployeeNameById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepo.findById(id);
        Employee employee = optionalEmployee.get();
        return mapper.map(employee, ProfileDTO.class);
    }
}
