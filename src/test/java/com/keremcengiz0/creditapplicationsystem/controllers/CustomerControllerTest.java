package com.keremcengiz0.creditapplicationsystem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.keremcengiz0.creditapplicationsystem.dtos.CustomerDTO;
import com.keremcengiz0.creditapplicationsystem.requests.CustomerCreateRequest;
import com.keremcengiz0.creditapplicationsystem.services.abstracts.CustomerService;
import com.keremcengiz0.creditapplicationsystem.utils.TestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;
    private ObjectMapper objectMapper;

    CustomerController customerController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        customerController = new CustomerController(customerService);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void save() throws Exception {
        CustomerCreateRequest customerCreateRequest = TestDataFactory.prepareCustomerCreateRequest();

        CustomerDTO expectedResponse = TestDataFactory.prepareCustomerDTO();

        when(customerService.save(customerCreateRequest)).thenReturn(expectedResponse);

       MvcResult result = mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerCreateRequest)))
                        .andExpect(status().isOk())
                        .andReturn();

        CustomerDTO actualResponse = objectMapper.readValue(result.getResponse().getContentAsString(),CustomerDTO.class);

        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);

        verify(customerService, times(1)).save(customerCreateRequest);
    }

}