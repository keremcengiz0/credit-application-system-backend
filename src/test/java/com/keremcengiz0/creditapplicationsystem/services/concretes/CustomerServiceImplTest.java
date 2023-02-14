package com.keremcengiz0.creditapplicationsystem.services.concretes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.keremcengiz0.creditapplicationsystem.dtos.CustomerDTO;
import com.keremcengiz0.creditapplicationsystem.entities.Customer;
import com.keremcengiz0.creditapplicationsystem.mappers.CustomerMapper;
import com.keremcengiz0.creditapplicationsystem.repositories.CustomerRepository;
import com.keremcengiz0.creditapplicationsystem.requests.CustomerCreateRequest;
import com.keremcengiz0.creditapplicationsystem.services.abstracts.CustomerService;
import com.keremcengiz0.creditapplicationsystem.utils.CustomerTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    private CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerServiceImpl(customerRepository, customerMapper);
    }

    @Test
    void save_WhenProperInputIsGiven_ThenShouldSuccess() {
        CustomerCreateRequest customerCreateRequest = CustomerTestDataFactory.prepareCustomerCreateRequest();
        CustomerDTO expectedResponse = CustomerTestDataFactory.prepareCustomerDTOForCreate();

        when(customerRepository.existsByIdentityNumber(customerCreateRequest.getIdentityNumber())).thenReturn(false);
        when(customerRepository.existsByPhoneNumber(customerCreateRequest.getPhoneNumber())).thenReturn(false);

        CustomerDTO actualResponse = customerService.save(customerCreateRequest);

        assertEquals(actualResponse.getIdentityNumber(), expectedResponse.getIdentityNumber());
        assertEquals(actualResponse.getFirstName(), expectedResponse.getFirstName());
        assertEquals(actualResponse.getLastName(), expectedResponse.getLastName());
        assertEquals(actualResponse.getPhoneNumber(), expectedResponse.getPhoneNumber());
        assertEquals(actualResponse.getBirthDate(), expectedResponse.getBirthDate());

        verify(customerRepository, times(1)).existsByIdentityNumber(customerCreateRequest.getIdentityNumber());
        verify(customerRepository, times(1)).existsByPhoneNumber(customerCreateRequest.getPhoneNumber());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }
}