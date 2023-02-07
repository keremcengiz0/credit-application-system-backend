package com.keremcengiz0.creditapplicationsystem.services.concretes;

import com.keremcengiz0.creditapplicationsystem.dtos.CustomerDTO;
import com.keremcengiz0.creditapplicationsystem.mappers.CustomerMapper;
import com.keremcengiz0.creditapplicationsystem.repositories.ApplicationRepository;
import com.keremcengiz0.creditapplicationsystem.repositories.CustomerRepository;
import com.keremcengiz0.creditapplicationsystem.services.abstracts.ApplicationService;
import com.keremcengiz0.creditapplicationsystem.services.abstracts.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ICustomerService implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ApplicationService applicationService;
    private final CustomerMapper customerMapper;

    @Autowired
    public ICustomerService(CustomerRepository customerRepository, ApplicationService applicationService, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.applicationService = applicationService;
        this.customerMapper = customerMapper;
    }

    @Override
    public CustomerDTO save(CustomerDTO customerDTO) {
        return null;
    }

    @Override
    public CustomerDTO update(CustomerDTO customerDTO) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<CustomerDTO> getAll() {
        return null;
    }

    @Override
    public CustomerDTO get(Long id) {
        return null;
    }
}
