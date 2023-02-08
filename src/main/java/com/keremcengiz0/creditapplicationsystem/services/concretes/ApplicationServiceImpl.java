package com.keremcengiz0.creditapplicationsystem.services.concretes;

import com.keremcengiz0.creditapplicationsystem.dtos.ApplicationDTO;
import com.keremcengiz0.creditapplicationsystem.dtos.CustomerDTO;
import com.keremcengiz0.creditapplicationsystem.entities.Application;
import com.keremcengiz0.creditapplicationsystem.entities.Customer;
import com.keremcengiz0.creditapplicationsystem.enums.CreditLimitMultiplier;
import com.keremcengiz0.creditapplicationsystem.enums.CreditResult;
import com.keremcengiz0.creditapplicationsystem.exceptions.NotFoundException;
import com.keremcengiz0.creditapplicationsystem.exceptions.UserNotFoundException;
import com.keremcengiz0.creditapplicationsystem.mappers.ApplicationMapper;
import com.keremcengiz0.creditapplicationsystem.mappers.CustomerMapper;
import com.keremcengiz0.creditapplicationsystem.repositories.ApplicationErrorRepository;
import com.keremcengiz0.creditapplicationsystem.repositories.ApplicationRepository;
import com.keremcengiz0.creditapplicationsystem.requests.ApplicationCreateRequest;
import com.keremcengiz0.creditapplicationsystem.services.abstracts.ApplicationService;
import com.keremcengiz0.creditapplicationsystem.services.abstracts.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationErrorRepository applicationErrorRepository;
    private final ApplicationRepository applicationRepository;
    private final ScoreService scoreService;
    private final MessageService messageService;
    private final ApplicationMapper applicationMapper;
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @Autowired
    public ApplicationServiceImpl(ApplicationRepository applicationRepository, ScoreService scoreService,
                                  MessageService messageService, ApplicationMapper applicationMapper,
                                  CustomerService customerService,
                                  ApplicationErrorRepository applicationErrorRepository, CustomerMapper customerMapper) {
        this.applicationRepository = applicationRepository;
        this.scoreService = scoreService;
        this.messageService = messageService;
        this.applicationMapper = applicationMapper;
        this.customerService = customerService;
        this.applicationErrorRepository = applicationErrorRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public ApplicationDTO makeAnApplication(ApplicationCreateRequest applicationCreateRequest, String identityNumber) {

        CustomerDTO customerDTO = this.customerService.findCustomerByIdentityNumber(identityNumber);
        Customer customer = this.customerMapper.fromCustomerDtoToCustomer(customerDTO);

        if(customer == null) {
            throw new UserNotFoundException("The customer to apply with " + customer.getId() + " id could not be found!");
        }

        this.scoreService.generateRandomScore();

        Map<String, Object> result = applicationResult(this.scoreService.getScore(customer.getIdentityNumber()), applicationCreateRequest.getSalary());
        log.info("Credit Score: " + this.scoreService.getScore(customer.getIdentityNumber()));
        BigDecimal creditLimit = (BigDecimal) result.get("creditLimit");
        CreditResult creditResult = (CreditResult) result.get("creditResult");

        Application application = Application.builder()
                .customer(customer)
                .creditLimit(creditLimit)
                .creditResult(creditResult)
                .creditScore(this.scoreService.getScore(customer.getIdentityNumber()))
                .salary(applicationCreateRequest.getSalary())
                .build();

        Boolean status = (application.getCreditResult() == CreditResult.CONFIRMED);
        this.messageService.sendSms(customer.getPhoneNumber(), status);
        log.info("ApplicationService: SMS sent.");

        return this.applicationMapper.fromApplicationToApplicationDto(this.applicationRepository.save(application));
    }

    @Override
    public List<ApplicationDTO> getAll() {
        List<Application> applications = this.applicationRepository.findAll();
        return this.applicationMapper.fromApplicationListToApplicationDtoList(applications);
    }

    @Override
    public List<ApplicationDTO> getStatusWithParam(String identityNumber, LocalDate birthDate) {
        List<Application> applicationList = this.applicationRepository.getAllApplicationsByCustomerIdentityNumberAndBirthdate(identityNumber, birthDate);

        if(applicationList.isEmpty()) {
            throw new NotFoundException("The applications for" + identityNumber + " number and " + birthDate + " birthdate was not found!");
        }

        return this.applicationMapper.fromApplicationListToApplicationDtoList(applicationList);
    }


    @Override
    public Map<String, Object> applicationResult(int score, BigDecimal salary) {
        Map<String, Object> resultMap = new HashMap<>();
        if(score < 500){
            resultMap.put("creditLimit", BigDecimal.valueOf(0));
            resultMap.put("creditResult", CreditResult.UNCONFIRMED);
        }else if(score >= 500 && score < 1000 && salary.intValue() <= 5000){
            resultMap.put("creditLimit", BigDecimal.valueOf(10000));
            resultMap.put("creditResult", CreditResult.CONFIRMED);
        }else if(score >= 500 && score < 1000 && (salary.intValue() > 5000 && salary.intValue() <10000)){
            resultMap.put("creditLimit", BigDecimal.valueOf(20000));
            resultMap.put("creditResult", CreditResult.CONFIRMED);
        }else if(score >= 500 && score < 1000 && salary.intValue() > 10000){
        resultMap.put("creditLimit", salary.multiply(BigDecimal.valueOf(CreditLimitMultiplier.CREDIT_LIMIT_MULTIPLIER.getValue()/2)));
        resultMap.put("creditResult", CreditResult.CONFIRMED);
       }else if(score >= 1000){
            resultMap.put("creditLimit", salary.multiply(BigDecimal.valueOf(CreditLimitMultiplier.CREDIT_LIMIT_MULTIPLIER.getValue())));
            resultMap.put("creditResult", CreditResult.CONFIRMED);
        }else{
            resultMap.put("creditLimit", BigDecimal.valueOf(0));
            resultMap.put("creditResult", CreditResult.UNCONFIRMED);
        }
        return resultMap;
    }

}
