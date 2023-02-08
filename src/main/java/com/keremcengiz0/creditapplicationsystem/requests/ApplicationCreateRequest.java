package com.keremcengiz0.creditapplicationsystem.requests;

import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Data
public class ApplicationCreateRequest {
    @DecimalMax(value = "999999", message = "Salary should be no more than 999999₺")
    @DecimalMin(value = "2000", message = "Salary must be at least 2000₺")
    @Digits(integer = 6, fraction = 0)
    private BigDecimal salary;

    @DecimalMax(value = "999999", message = "Guarantee should be no more than 999999₺")
    @DecimalMin(value = "2000", message = "Guarantee must be at least 2000₺")
    @Digits(integer = 6, fraction = 0)
    private BigDecimal guarantee;
}
