package com.keremcengiz0.creditapplicationsystem.services.abstracts;

import com.keremcengiz0.creditapplicationsystem.dtos.ApplicationDTO;
import com.keremcengiz0.creditapplicationsystem.requests.ApplicationCreateRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ApplicationService {
    ApplicationDTO makeAnApplication(ApplicationCreateRequest applicationCreateRequest, String identityNumber);
    List<ApplicationDTO> getAll();
    ApplicationDTO getStatus(String identityNumber);
    Map<String, Object> applicationResult(int score, BigDecimal salary);
}
