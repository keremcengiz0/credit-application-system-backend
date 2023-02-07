package com.keremcengiz0.creditapplicationsystem.services.abstracts;

import com.keremcengiz0.creditapplicationsystem.dtos.CustomerDTO;

import java.util.List;

public interface CustomerService {
    CustomerDTO save(CustomerDTO customerDTO);
    CustomerDTO update(CustomerDTO customerDTO);
    void delete(Long id);
    List<CustomerDTO> getAll();
    CustomerDTO get(Long id);
}
