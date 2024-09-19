package com.ReferralHub.controllers;

import com.ReferralHub.DTO.EmployeeListDTO;
import com.ReferralHub.entities.ApiResponse;
import com.ReferralHub.entities.Employee;
import com.ReferralHub.entities.Job;
import com.ReferralHub.services.EmployeeServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin()
public class AdminController {

    @Autowired
    private EmployeeServiceIMPL employeeService;

    @GetMapping("/approvedEmps")
    public ResponseEntity<?> getApprovedEmpsList(){
        return ResponseEntity.ok(employeeService.getApprovedEmp());
    }

    @GetMapping("/pendingEmps")
    public ResponseEntity<?> getPendingEmpsList(){
        return ResponseEntity.ok(employeeService.getPendingEmp());
    }

    @PutMapping("{id}/status")
    public ResponseEntity<Employee> updateEmpStatus(@PathVariable Long id){
        Employee updatedEmployee = employeeService.changeEmpStatus(id);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/deleteEmp/{id}")
    public ResponseEntity<ApiResponse> deleteEmployee(@PathVariable Long id){

        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(new ApiResponse("Employee Deleted"));
    }

    @GetMapping("/getEmployeeByName/{name}")
    public ResponseEntity<?> searchEmployeeByName(@PathVariable String name){
        Employee employee = employeeService.getEmployeeName(name);
        if(employee != null){
            return ResponseEntity.ok(employee);
        }else{
            return ResponseEntity.status(500).body(new ApiResponse("Cannot fetch employee"));
        }
    }

}
