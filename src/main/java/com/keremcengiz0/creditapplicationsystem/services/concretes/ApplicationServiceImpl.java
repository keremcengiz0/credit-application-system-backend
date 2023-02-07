package com.keremcengiz0.creditapplicationsystem.services.concretes;

import com.keremcengiz0.creditapplicationsystem.dtos.ApplicationDTO;
import com.keremcengiz0.creditapplicationsystem.entities.Application;
import com.keremcengiz0.creditapplicationsystem.entities.Customer;
import com.keremcengiz0.creditapplicationsystem.enums.CreditLimitMultiplier;
import com.keremcengiz0.creditapplicationsystem.enums.CreditResult;
import com.keremcengiz0.creditapplicationsystem.exceptions.NotFoundException;
import com.keremcengiz0.creditapplicationsystem.mappers.ApplicationMapper;
import com.keremcengiz0.creditapplicationsystem.repositories.ApplicationRepository;
import com.keremcengiz0.creditapplicationsystem.services.abstracts.ApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ScoreService scoreService;
    private final MessageService messageService;
    private final ApplicationMapper applicationMapper;

    @Autowired
    public ApplicationServiceImpl(ApplicationRepository applicationRepository, ScoreService scoreService,
                                  MessageService messageService, ApplicationMapper applicationMapper) {
        this.applicationRepository = applicationRepository;
        this.scoreService = scoreService;
        this.messageService = messageService;
        this.applicationMapper = applicationMapper;
    }

    @Override
    public ApplicationDTO makeAnApplication(Customer customer) {
        Map<String, Object> result = applicationResult(this.scoreService.getScore(customer.getIdentityNumber()), customer.getSalary());
        BigDecimal creditLimit = (BigDecimal) result.get("creditLimit");
        CreditResult creditResult = (CreditResult) result.get("creditResult");

        Application application = Application.builder()
                .customer(customer)
                .creditLimit(creditLimit)
                .creditResult(creditResult)
                .build();

        Boolean status = (application.getCreditResult() == CreditResult.CONFIRMED);
        this.messageService.sendSms(customer.getPhoneNumber(), status);
        log.info("ApplicationService: SMS sent.");

        return this.applicationMapper.fromApplicationToApplicationDto(this.applicationRepository.save(application));
    }

    @Override
    public ApplicationDTO update(Customer customer, Long applicationId) {
        Map<String, Object> result = applicationResult(this.scoreService.getScore(customer.getIdentityNumber()), customer.getSalary());
        BigDecimal creditLimit = (BigDecimal) result.get("creditLimit");
        CreditResult creditResult = (CreditResult) result.get("creditResult");

        Application application = Application.builder()
                .customer(customer)
                .creditLimit(creditLimit)
                .creditResult(creditResult)
                .build();

        Boolean status = (application.getCreditResult() == CreditResult.CONFIRMED);
        application.setId(applicationId);
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
    public ApplicationDTO getStatus(String identityNumber) {
        Optional<Application> optionalApplication = Optional.ofNullable(this.applicationRepository.findByCustomerIdentityNumber(identityNumber));

        if(!optionalApplication.isPresent()) {
            throw new NotFoundException("The application for" + identityNumber + " number was not found!");
        }
        Application application = optionalApplication.get();
        return this.applicationMapper.fromApplicationToApplicationDto(application);
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
        }else if(score >= 500 && score < 1000 && salary.intValue() > 5000){
            resultMap.put("creditLimit", BigDecimal.valueOf(20000));
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
