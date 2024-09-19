package com.ReferralHub.services;

import com.ReferralHub.DTO.CandidateDTO;
import com.ReferralHub.DTO.CandidateListDTO;
import com.ReferralHub.DTO.ReferCandidateDTO;
import com.ReferralHub.entities.Candidate;
import com.ReferralHub.entities.Status;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Stack;

@Service
public interface CandidateService {

    Candidate addNewCandidate(ReferCandidateDTO refercandidateDTO, MultipartFile file) throws IOException;

    Candidate changeStatusAndCalcBonus(Long candidateId, Long jobId, Status status);

    List<CandidateDTO> getReferredCandidates(Long jobId);

    Candidate referOldCandidate(String email, Long jobId);

    List<CandidateDTO> getReferredCandidatesByEmployee(Long employeeId);
}
