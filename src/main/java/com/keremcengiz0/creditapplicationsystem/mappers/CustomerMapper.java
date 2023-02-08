package com.keremcengiz0.creditapplicationsystem.mappers;

import com.keremcengiz0.creditapplicationsystem.dtos.CustomerDTO;
import com.keremcengiz0.creditapplicationsystem.entities.Customer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer fromCustomerDtoToCustomer(CustomerDTO customerDTO);
    CustomerDTO fromCustomerToCustomerDto(Customer customer);
    List<CustomerDTO> fromCustomerListToCustomerDtoList(List<Customer> customers);

}
