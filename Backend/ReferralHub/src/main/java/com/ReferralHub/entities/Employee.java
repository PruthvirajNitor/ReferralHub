package com.ReferralHub.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
@Getter
@Setter
public class Employee  extends BaseEntity implements UserDetails {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String mobileNum;
    private Double salary;
    private Double bonus;

    @Enumerated(EnumType.STRING)
    private Role role;
    private Boolean status;

    @Enumerated(EnumType.STRING)
    private Department department;

    @OneToMany(mappedBy = "referredBy")
    private List<Candidate> referredCandidates = new ArrayList<>();

    private String imagePath;


    public Employee(){}

    public Employee(Long id, String firstName, String lastName, String email, String mobileNum, Double salary, Department department, List<Candidate> referredCandidates, Role role,String imagePath,Boolean status,String password) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobileNum = mobileNum;
        this.salary = salary;
        this.department = department;
        this.referredCandidates = referredCandidates;
        this.role = role;
        this.imagePath=imagePath;
        this.status=status;
        this.password=password;

    }

    public Employee(String firstName, String lastName,String email,String mobileNum,String imagePath){
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.mobileNum=mobileNum;
        this.imagePath=imagePath;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "imagePath='" + imagePath + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", mobileNum='" + mobileNum + '\'' +
                ", salary=" + salary +
                ", department=" + department +
                ", referredCandidates=" + referredCandidates +
                ", role=" + role +
                ", status=" + status +
                '}';
    }

    @Override
    public Collection<? extends SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> list = List.of(new SimpleGrantedAuthority("ROLE_"+role.name()));
        return list;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

