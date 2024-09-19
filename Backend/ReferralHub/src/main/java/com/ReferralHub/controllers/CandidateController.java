package com.ReferralHub.controllers;

import com.ReferralHub.entities.ApiResponse;
import com.ReferralHub.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/candidate")
@CrossOrigin()
public class CandidateController {

    @Autowired
    private EmailService emailService;

//    @PostMapping("/sendMail")
//    public ResponseEntity<ApiResponse> sendMail(@RequestBody String email){
//       emailService.sendSimpleEmail(email);
//
//       return ResponseEntity.ok(new ApiResponse("Mail sent"));
//    }
}
