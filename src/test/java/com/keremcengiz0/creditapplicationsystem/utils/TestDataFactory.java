package com.keremcengiz0.creditapplicationsystem.utils;

import com.keremcengiz0.creditapplicationsystem.dtos.CustomerDTO;
import com.keremcengiz0.creditapplicationsystem.requests.CustomerCreateRequest;

import java.time.LocalDate;

public class TestDataFactory {

    public static CustomerCreateRequest prepareCustomerCreateRequest() {
        return CustomerCreateRequest.builder()
                .identityNumber("12345678912")
                .firstName("Kerem")
                .lastName("Cengiz")
                .phoneNumber("1472583698")
                .birthDate(LocalDate.of(1999, 7, 31))
                .build();
    }

    public static CustomerDTO prepareCustomerDTO() {
        return CustomerDTO.builder()
                .id(1L)
                .identityNumber("12345678912")
                .firstName("Kerem")
                .lastName("Cengiz")
                .phoneNumber("1472583698")
                .birthDate(LocalDate.of(1999, 7, 31))
                .build();
    }
}
