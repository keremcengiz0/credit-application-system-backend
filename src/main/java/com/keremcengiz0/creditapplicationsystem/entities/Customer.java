package com.keremcengiz0.creditapplicationsystem.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@Entity
@Table(name = "customers")
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name ="identity_number", nullable = false, length = 11)
    @NotNull(message = "identity_number cannot be null!")
    private String identityNumber;

    @Column(name ="first_name", nullable = false, length = 50)
    @NotNull(message = "first_name cannot be null!")
    private String firstName;

    @Column(name ="first_name", nullable = false, length = 50)
    @NotNull(message = "first_name cannot be null!")
    private String lastName;

    @Column(name = "salary", nullable = false, precision=6, scale=0)
    private BigDecimal salary;

    @Column(name = "phone_number", nullable = false, length = 10)
    private String phoneNumber;

    @Column(name ="birth_date", nullable = false, length = 50)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Column(name = "guarantee", nullable = true, precision=6, scale=0)
    private BigDecimal guarantee;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Application application;
}
