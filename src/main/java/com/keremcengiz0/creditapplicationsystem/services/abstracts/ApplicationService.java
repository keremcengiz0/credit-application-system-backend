package com.keremcengiz0.creditapplicationsystem.services.abstracts;

import com.keremcengiz0.creditapplicationsystem.dtos.ApplicationDTO;
import com.keremcengiz0.creditapplicationsystem.entities.Customer;
import com.keremcengiz0.creditapplicationsystem.enums.CreditResult;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ApplicationService {
    ApplicationDTO makeAnApplication(Customer customer);
    ApplicationDTO update(Customer customer, Long applicationId);
    List<ApplicationDTO> getAll();
    ApplicationDTO getStatus(String identityNumber);
    Map<String, Object> applicationResult(int score, BigDecimal salary);
}
