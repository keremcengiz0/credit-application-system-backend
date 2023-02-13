package com.keremcengiz0.creditapplicationsystem.mappers;

import com.keremcengiz0.creditapplicationsystem.dtos.CustomerDTO;
import com.keremcengiz0.creditapplicationsystem.entities.Customer;
import com.keremcengiz0.creditapplicationsystem.requests.CustomerCreateRequest;
import com.keremcengiz0.creditapplicationsystem.requests.CustomerUpdateRequest;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer fromCustomerDtoToCustomer(CustomerDTO customerDTO);
    CustomerDTO fromCustomerToCustomerDto(Customer customer);
    CustomerDTO fromCustomerCreateRequestToCustomerDto(CustomerCreateRequest customerCreateRequest);
    CustomerDTO fromCustomerUpdateRequestToCustomerDto(CustomerUpdateRequest customerUpdateRequest);
    List<CustomerDTO> fromCustomerListToCustomerDtoList(List<Customer> customers);

}
