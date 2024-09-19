package com.ReferralHub.repositories;

import com.ReferralHub.DTO.EmployeeListDTO;
import com.ReferralHub.entities.Employee;
import com.ReferralHub.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    List<Employee> findByStatus(Boolean status);


    @Query("SELECT e FROM Employee e WHERE CONCAT(e.firstName, ' ', e.lastName) = :fullName")
    Employee findByFullName(@Param("fullName") String fullName);

    boolean existsByEmail(String email);
    Optional<Employee> findByEmail(String email);
}
