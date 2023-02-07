package com.keremcengiz0.creditapplicationsystem.mappers;

import com.keremcengiz0.creditapplicationsystem.dtos.CustomerDTO;
import com.keremcengiz0.creditapplicationsystem.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Named("fromCustomerDtoToCustomer")
    Customer fromCustomerDtoToCustomer(CustomerDTO customerDTO);

    @Named("fromCustomerToCustomerDto")
    CustomerDTO fromCustomerToCustomerDto(Customer customer);

    @Named("fromCustomerListToCustomerDtoList")
    List<CustomerDTO> fromCustomerListToCustomerDtoList(List<Customer> customers);

}
