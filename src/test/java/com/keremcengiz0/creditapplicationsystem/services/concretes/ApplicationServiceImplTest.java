package com.keremcengiz0.creditapplicationsystem.services.concretes;

import com.keremcengiz0.creditapplicationsystem.dtos.ApplicationDTO;
import com.keremcengiz0.creditapplicationsystem.dtos.ApplicationResultDTO;
import com.keremcengiz0.creditapplicationsystem.dtos.CustomerDTO;
import com.keremcengiz0.creditapplicationsystem.entities.Application;
import com.keremcengiz0.creditapplicationsystem.enums.CreditResult;
import com.keremcengiz0.creditapplicationsystem.mappers.ApplicationMapper;
import com.keremcengiz0.creditapplicationsystem.mappers.CustomerMapper;
import com.keremcengiz0.creditapplicationsystem.repositories.ApplicationErrorRepository;
import com.keremcengiz0.creditapplicationsystem.repositories.ApplicationRepository;
import com.keremcengiz0.creditapplicationsystem.requests.ApplicationCreateRequest;
import com.keremcengiz0.creditapplicationsystem.services.abstracts.ApplicationService;
import com.keremcengiz0.creditapplicationsystem.services.abstracts.CustomerService;
import com.keremcengiz0.creditapplicationsystem.utils.ApplicationTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceImplTest {
    @Mock
    ApplicationRepository applicationRepository;
    @Mock
    ApplicationErrorRepository applicationErrorRepository;
    private CustomerMapper customerMapper = CustomerMapper.INSTANCE;
    private ApplicationMapper applicationMapper = ApplicationMapper.INSTANCE;
    @Mock
    CustomerService customerService;
    @Mock
    ScoreService scoreService;
    @Mock
    MessageService messageService;
    ApplicationService applicationService;

    @BeforeEach
    void setUp() {
        applicationService = new ApplicationServiceImpl(applicationRepository, scoreService, messageService,
                applicationMapper, customerService, applicationErrorRepository, customerMapper );
    }

    @Test
    void makeAnApplication_ScoreBelow500_ReturnUnconfirmed() {
        ApplicationCreateRequest applicationCreateRequest = ApplicationCreateRequest.builder()
                .salary(BigDecimal.valueOf(4000))
                .guarantee(BigDecimal.valueOf(2000))
                .build();

        CustomerDTO customerDTO = ApplicationTestDataFactory.prepareApplicationDTOForUnconfirmed().getCustomer();
        ApplicationDTO applicationDTO = ApplicationTestDataFactory.prepareApplicationDTOForUnconfirmed();
        Application expectedResponse = applicationMapper.fromApplicationDtoToApplication(applicationDTO);

        when(customerService.findCustomerByIdentityNumber(customerDTO.getIdentityNumber())).thenReturn(customerDTO);
        when(scoreService.generateRandomScore(customerDTO.getIdentityNumber())).thenReturn(300);
        when(applicationRepository.save(any(Application.class))).thenReturn(expectedResponse);

        ApplicationDTO actualResponse = applicationService.makeAnApplication(applicationCreateRequest, customerDTO.getIdentityNumber());

        verify(messageService, times(1)).sendSms(customerDTO.getPhoneNumber(),false);
        verify(applicationRepository, times(1)).save(any(Application.class));
        assertNotNull(actualResponse);
        assertEquals(CreditResult.UNCONFIRMED, actualResponse.getCreditResult());
        assertEquals(BigDecimal.valueOf(0), actualResponse.getCreditLimit());
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }
}