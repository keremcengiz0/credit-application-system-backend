package com.keremcengiz0.creditapplicationsystem.requests;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ApplicationUpdateRequest {
    private BigDecimal salary;
}
