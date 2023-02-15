package com.keremcengiz0.creditapplicationsystem.services.concretes;

import com.keremcengiz0.creditapplicationsystem.dtos.ApplicationDTO;
import com.keremcengiz0.creditapplicationsystem.dtos.CustomerDTO;
import com.keremcengiz0.creditapplicationsystem.entities.Application;
import com.keremcengiz0.creditapplicationsystem.entities.Customer;
import com.keremcengiz0.creditapplicationsystem.enums.CreditResult;
import com.keremcengiz0.creditapplicationsystem.exceptions.UserNotFoundException;
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
import java.util.List;
import java.util.Optional;

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
    void makeAnApplication_WhenScoreBelow500_ThenReturnUnconfirmedApplication() {
        ApplicationCreateRequest applicationCreateRequest = ApplicationCreateRequest.builder()
                .salary(BigDecimal.valueOf(4000))
                .guarantee(BigDecimal.valueOf(2000))
                .build();

        CustomerDTO customerDTO = ApplicationTestDataFactory.prepareApplicationDTOForUnconfirmed().getCustomer();
        ApplicationDTO applicationDTO = ApplicationTestDataFactory.prepareApplicationDTOForUnconfirmed();
        Application expectedResponse = applicationMapper.fromApplicationDtoToApplication(applicationDTO);

        when(customerService.findCustomerByIdentityNumber(customerDTO.getIdentityNumber())).thenReturn(customerDTO);
        when(scoreService.getScore(customerDTO.getIdentityNumber())).thenReturn(300);
        when(applicationRepository.save(any(Application.class))).thenReturn(expectedResponse);

        ApplicationDTO actualResponse = applicationService.makeAnApplication(applicationCreateRequest, customerDTO.getIdentityNumber());

        verify(messageService, times(1)).sendSms(customerDTO.getPhoneNumber(),false);
        verify(applicationRepository, times(1)).save(any(Application.class));
        assertNotNull(actualResponse);
        assertEquals(CreditResult.UNCONFIRMED, actualResponse.getCreditResult());
        assertEquals(BigDecimal.valueOf(0), actualResponse.getCreditLimit());
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    void makeAnApplication_WhenScoreBetween500And1000AndSalarySmallerThan5000_ThenReturnConfirmedApplication() {
        ApplicationCreateRequest applicationCreateRequest = ApplicationCreateRequest.builder()
                .salary(BigDecimal.valueOf(4000))
                .guarantee(BigDecimal.valueOf(2000))
                .build();

        CustomerDTO customerDTO = ApplicationTestDataFactory.prepareApplicationDTOForScoreBetween500And1000AndSalarySmallerThan5000ConfirmedApplication().getCustomer();
        ApplicationDTO applicationDTO = ApplicationTestDataFactory.prepareApplicationDTOForScoreBetween500And1000AndSalarySmallerThan5000ConfirmedApplication();
        Application expectedResponse = applicationMapper.fromApplicationDtoToApplication(applicationDTO);

        when(customerService.findCustomerByIdentityNumber(customerDTO.getIdentityNumber())).thenReturn(customerDTO);
        when(scoreService.getScore(customerDTO.getIdentityNumber())).thenReturn(600);
        when(applicationRepository.save(any(Application.class))).thenReturn(expectedResponse);

        ApplicationDTO actualResponse = applicationService.makeAnApplication(applicationCreateRequest, customerDTO.getIdentityNumber());

        verify(messageService, times(1)).sendSms(customerDTO.getPhoneNumber(),true);
        verify(applicationRepository, times(1)).save(any(Application.class));
        assertNotNull(actualResponse);
        assertEquals(CreditResult.CONFIRMED, actualResponse.getCreditResult());
        assertEquals(BigDecimal.valueOf(10200), actualResponse.getCreditLimit());
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    void makeAnApplication_WhenScoreBetween500And1000AndSalaryBetween5000And10000_ThenReturnConfirmedApplication() {
        ApplicationCreateRequest applicationCreateRequest = ApplicationCreateRequest.builder()
                .salary(BigDecimal.valueOf(8000))
                .guarantee(BigDecimal.valueOf(4000))
                .build();

        CustomerDTO customerDTO = ApplicationTestDataFactory.prepareApplicationDTOForScoreBetween500And1000AndSalaryBetween5000And10000ConfirmedApplication().getCustomer();
        ApplicationDTO applicationDTO = ApplicationTestDataFactory.prepareApplicationDTOForScoreBetween500And1000AndSalaryBetween5000And10000ConfirmedApplication();
        Application expectedResponse = applicationMapper.fromApplicationDtoToApplication(applicationDTO);

        when(customerService.findCustomerByIdentityNumber(customerDTO.getIdentityNumber())).thenReturn(customerDTO);
        when(scoreService.getScore(customerDTO.getIdentityNumber())).thenReturn(600);
        when(applicationRepository.save(any(Application.class))).thenReturn(expectedResponse);

        ApplicationDTO actualResponse = applicationService.makeAnApplication(applicationCreateRequest, customerDTO.getIdentityNumber());

        verify(messageService, times(1)).sendSms(customerDTO.getPhoneNumber(),true);
        verify(applicationRepository, times(1)).save(any(Application.class));
        assertNotNull(actualResponse);
        assertEquals(CreditResult.CONFIRMED, actualResponse.getCreditResult());
        assertEquals(BigDecimal.valueOf(20800), actualResponse.getCreditLimit());
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    void makeAnApplication_WhenScoreBetween500And1000AndSalaryOver10000_ThenReturnConfirmedApplication() {
        ApplicationCreateRequest applicationCreateRequest = ApplicationCreateRequest.builder()
                .salary(BigDecimal.valueOf(12000))
                .guarantee(BigDecimal.valueOf(8000))
                .build();

        CustomerDTO customerDTO = ApplicationTestDataFactory.prepareApplicationDTOForScoreBetween500And1000AndSalaryOver10000ConfirmedApplication().getCustomer();
        ApplicationDTO applicationDTO = ApplicationTestDataFactory.prepareApplicationDTOForScoreBetween500And1000AndSalaryOver10000ConfirmedApplication();
        Application expectedResponse = applicationMapper.fromApplicationDtoToApplication(applicationDTO);

        when(customerService.findCustomerByIdentityNumber(customerDTO.getIdentityNumber())).thenReturn(customerDTO);
        when(scoreService.getScore(customerDTO.getIdentityNumber())).thenReturn(700);
        when(applicationRepository.save(any(Application.class))).thenReturn(expectedResponse);

        ApplicationDTO actualResponse = applicationService.makeAnApplication(applicationCreateRequest, customerDTO.getIdentityNumber());

        verify(messageService, times(1)).sendSms(customerDTO.getPhoneNumber(),true);
        verify(applicationRepository, times(1)).save(any(Application.class));
        assertNotNull(actualResponse);
        assertEquals(CreditResult.CONFIRMED, actualResponse.getCreditResult());
        assertEquals(BigDecimal.valueOf(26000), actualResponse.getCreditLimit());
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    void makeAnApplication_WhenScoreOver1000_ThenReturnConfirmedApplication() {
        ApplicationCreateRequest applicationCreateRequest = ApplicationCreateRequest.builder()
                .salary(BigDecimal.valueOf(15000))
                .guarantee(BigDecimal.valueOf(10000))
                .build();

        CustomerDTO customerDTO = ApplicationTestDataFactory.prepareApplicationDTOForScoreOver1000ConfirmedApplication().getCustomer();
        ApplicationDTO applicationDTO = ApplicationTestDataFactory.prepareApplicationDTOForScoreOver1000ConfirmedApplication();
        Application expectedResponse = applicationMapper.fromApplicationDtoToApplication(applicationDTO);

        when(customerService.findCustomerByIdentityNumber(customerDTO.getIdentityNumber())).thenReturn(customerDTO);
        when(scoreService.getScore(customerDTO.getIdentityNumber())).thenReturn(1200);
        when(applicationRepository.save(any(Application.class))).thenReturn(expectedResponse);

        ApplicationDTO actualResponse = applicationService.makeAnApplication(applicationCreateRequest, customerDTO.getIdentityNumber());

        verify(messageService, times(1)).sendSms(customerDTO.getPhoneNumber(),true);
        verify(applicationRepository, times(1)).save(any(Application.class));
        assertNotNull(actualResponse);
        assertEquals(CreditResult.CONFIRMED, actualResponse.getCreditResult());
        assertEquals(BigDecimal.valueOf(70000), actualResponse.getCreditLimit());
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    void makeAnApplication_WhenCustomerIsNull_ThenThrowUserNotFoundException() {
        ApplicationCreateRequest applicationCreateRequest = ApplicationCreateRequest.builder()
                .salary(BigDecimal.valueOf(15000))
                .guarantee(BigDecimal.valueOf(10000))
                .build();

        String identityNumber = "55555555555";

        when(customerService.findCustomerByIdentityNumber(anyString())).thenReturn(null);

      assertThrows(UserNotFoundException.class, () -> {
            applicationService.makeAnApplication(applicationCreateRequest, identityNumber);
        });

    }

    @Test
    void getAllApplication_WhenProperInputIsGiven_ThenShouldReturnApplicationDTOList() {

        List<ApplicationDTO> applicationDTOList = ApplicationTestDataFactory.prepareApplicationDTOForGetAll();
        List<Application> applicationList = applicationMapper.fromApplicationDtoListToApplicationList(applicationDTOList);

        when(applicationRepository.findAll()).thenReturn(applicationList);

        List<ApplicationDTO> actualApplicationDTOList = applicationService.getAll();

        assertEquals(applicationList.size(), actualApplicationDTOList.size());
        assertEquals(applicationDTOList, actualApplicationDTOList);

    }


}