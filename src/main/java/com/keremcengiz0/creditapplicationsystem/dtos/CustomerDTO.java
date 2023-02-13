package com.keremcengiz0.creditapplicationsystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private Long id;
    private String identityNumber;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthDate;
}
