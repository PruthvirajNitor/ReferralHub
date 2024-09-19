package com.ReferralHub.controllers;

import com.ReferralHub.DTO.EmployeeListDTO;
import com.ReferralHub.DTO.SignInResponse;
import com.ReferralHub.DTO.SigninDTO;
import com.ReferralHub.customException.CustomException;
import com.ReferralHub.entities.ApiResponse;
import com.ReferralHub.entities.Employee;
import com.ReferralHub.repositories.EmployeeRepository;
import com.ReferralHub.services.EmployeeService;
import com.ReferralHub.services.jwt.JwtService;
import com.ReferralHub.services.jwt.UserJwtImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    //    @Autowired
//    private AuthService authService;
    @Autowired
    private UserJwtImpl userJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SigninDTO loginDTO){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword())
            );
        }catch (AuthenticationException e){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDetails user;
        Optional<Employee> employeeOptional;
        try{
            user=userJwt.loadUserByUsername(loginDTO.getEmail());
            employeeOptional=employeeRepository.findByEmail(loginDTO.getEmail());
            Employee employee = employeeOptional.get();
            if(!employee.getStatus()){
                throw new CustomException("You are not approved");
            }

        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(404).build();
        }
        String jwt=jwtService.generateToken(user);

        return ResponseEntity.ok(new SignInResponse(jwt));
    }

    @PostMapping("/getByToken")
    public ResponseEntity<?> getUserByToken(@RequestBody SignInResponse response){
        try{
            String email= jwtService.extractUsername(response.getJwtToken());
            EmployeeListDTO dto=employeeService.findByEmail(email);

            if(dto!=null){
                return  ResponseEntity.ok(dto);
            }
            else {
                return ResponseEntity.status(404).body(new ApiResponse("Email not found"));
            }
        }catch (Exception e){
            return  ResponseEntity.status(500).build();
        }
    }
}