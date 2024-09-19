package com.ReferralHub.services.jwt;

import com.ReferralHub.customException.CustomException;
import com.ReferralHub.entities.Employee;
import com.ReferralHub.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserJwtImpl implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee emp= employeeRepo.findByEmail(email).orElseThrow(()->new CustomException("User not found"));
        return  new org.springframework.security.core.userdetails.User(emp.getEmail(),emp.getPassword(), emp.getAuthorities());

    }
}
