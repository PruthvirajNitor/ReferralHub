package com.ReferralHub.DTO;

import com.ReferralHub.entities.Status;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class JobStatusDTO {

    private Long id;
    private String comment;
    private Boolean status;

}
