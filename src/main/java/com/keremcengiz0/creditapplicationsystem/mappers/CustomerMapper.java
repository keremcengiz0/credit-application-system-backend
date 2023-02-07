package com.keremcengiz0.creditapplicationsystem.mappers;

import com.keremcengiz0.creditapplicationsystem.dtos.CustomerDTO;
import com.keremcengiz0.creditapplicationsystem.entities.Customer;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer fromCustomerDtoToCustomer(CustomerDTO customerDTO);
    CustomerDTO fromCustomerToCustomerDto(Customer customer);
    List<CustomerDTO> fromCustomerListToCustomerDtoList(List<Customer> customers);

}
