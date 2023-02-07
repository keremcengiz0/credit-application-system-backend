package com.keremcengiz0.creditapplicationsystem.services.concretes;

import com.keremcengiz0.creditapplicationsystem.dtos.ApplicationDTO;
import com.keremcengiz0.creditapplicationsystem.entities.Customer;
import com.keremcengiz0.creditapplicationsystem.enums.CreditResult;
import com.keremcengiz0.creditapplicationsystem.services.abstracts.ApplicationService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class IApplicationService implements ApplicationService {
    @Override
    public ApplicationDTO makeAnApplication(Customer customer) {
        return null;
    }

    @Override
    public ApplicationDTO update(Customer customer, Long applicationId) {
        return null;
    }

    @Override
    public List<ApplicationDTO> getAll() {
        return null;
    }

    @Override
    public ApplicationDTO getStatus(String identityNumber) {
        return null;
    }

    @Override
    public Map<BigDecimal, CreditResult> applicationResult(Integer score, BigDecimal salary) {
        return null;
    }
}
