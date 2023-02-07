package com.keremcengiz0.creditapplicationsystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private Long id;

    @NotBlank(message = "identity_number is required!")
    @Pattern(regexp = "(^[0-9]{11}$)", message = "The ID number must be 11 characters!")
    @Pattern(regexp = "(^\\d*[02468]$)", message = "The last digit of the ID number must be an even number!")
    private String identityNumber;

    @Size(min = 3, max = 50, message = "The First Name must contain between 3-50 characters!")
    @Pattern(regexp = "(^[a-zA-Z]{3,50}$)", message = "The First Name must be of characters!")
    private String firstName;

    @Size(min = 2, max = 50, message = "The First Name must contain between 3-50 characters!")
    @Pattern(regexp = "(^[a-zA-Z]{3,50}$)", message = "The First Name must be of characters!")
    private String lastName;

    @DecimalMax(value = "999999", message = "Salary should be no more than 999999₺")
    @DecimalMin(value = "2000", message = "Salary must be at least 2000₺")
    @Digits(integer = 6, fraction = 0)
    private BigDecimal salary;

    @NotBlank(message = "Phone Number is required!")
    @Pattern(regexp = "(^[0-9]{10}$)", message = "The phone number must consist of numbers and must be 10 characters (not including 0)!")
    private String phoneNumber;
}