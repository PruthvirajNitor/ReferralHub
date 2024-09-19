package com.ReferralHub.repositories;

import com.ReferralHub.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job,Long> {

    List<Job> findByStatus(Boolean status);

    Job findByTitle(String jobTitle);
}
