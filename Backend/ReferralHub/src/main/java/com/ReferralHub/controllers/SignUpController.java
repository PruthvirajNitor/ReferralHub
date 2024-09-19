package com.ReferralHub.controllers;

import com.ReferralHub.DTO.EmployeeDTO;
import com.ReferralHub.entities.ApiResponse;
import com.ReferralHub.services.EmployeeService;
import com.ReferralHub.services.jwt.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/signup")
public class SignUpController {

    private final AuthService authService;

    @Autowired
    private EmployeeService employeeService;

    public SignUpController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/addUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> addNewEmployee(@ModelAttribute EmployeeDTO employeeDTO, @RequestPart MultipartFile file) throws IOException {
        boolean isUserCreated=authService.createUser(employeeDTO,file);
        if(isUserCreated){
            return ResponseEntity.ok().body(new ApiResponse("New Employee Added, Wait for approval"));
        }
        else{
            return ResponseEntity.status(500).body(new ApiResponse("Unable to add employee"));
        }
//
    }
}
