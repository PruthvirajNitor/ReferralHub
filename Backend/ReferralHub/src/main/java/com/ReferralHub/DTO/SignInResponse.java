package com.ReferralHub.DTO;

import com.ReferralHub.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class SignInResponse {
    private String jwtToken;
}

