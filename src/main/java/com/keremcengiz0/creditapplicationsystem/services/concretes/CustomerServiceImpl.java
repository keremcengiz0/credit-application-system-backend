package com.keremcengiz0.creditapplicationsystem.services.concretes;

import com.keremcengiz0.creditapplicationsystem.dtos.CustomerDTO;
import com.keremcengiz0.creditapplicationsystem.entities.Customer;
import com.keremcengiz0.creditapplicationsystem.exceptions.IdentityNumberIsAlreadyExistException;
import com.keremcengiz0.creditapplicationsystem.exceptions.NotFoundException;
import com.keremcengiz0.creditapplicationsystem.exceptions.PhoneNumberIsAlreadyExistException;
import com.keremcengiz0.creditapplicationsystem.mappers.CustomerMapper;
import com.keremcengiz0.creditapplicationsystem.repositories.CustomerRepository;
import com.keremcengiz0.creditapplicationsystem.services.abstracts.ApplicationService;
import com.keremcengiz0.creditapplicationsystem.services.abstracts.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ApplicationService applicationService;
    protected final CustomerMapper customerMapper;


    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ApplicationService applicationService, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.applicationService = applicationService;
        this.customerMapper = customerMapper;
    }

    @Override
    public CustomerDTO save(CustomerDTO customerDTO) {
        if(this.customerRepository.existsByIdentityNumber(customerDTO.getIdentityNumber())) {
            throw new IdentityNumberIsAlreadyExistException(customerDTO.getIdentityNumber() + "--> This id number already exists.");
        }
        if(this.customerRepository.existsByPhoneNumber(customerDTO.getPhoneNumber())) {
            throw new PhoneNumberIsAlreadyExistException(customerDTO.getPhoneNumber() + "--> This phone number already exists.");
        }

        Customer customer = this.customerMapper.fromCustomerDtoToCustomer(customerDTO);
        this.customerRepository.save(customer);
        log.info("ICustomerService: The application has been created.");
        this.applicationService.makeAnApplication(customer);

        return this.customerMapper.fromCustomerToCustomerDto(customer);
    }

    @Override
    public CustomerDTO update(CustomerDTO customerDTO) {
        Customer toUpdateCustomer = this.customerRepository.findById(customerDTO.getId()).get();

        if(!toUpdateCustomer.getIdentityNumber().equals(customerDTO.getIdentityNumber())) {
            if(this.customerRepository.existsByIdentityNumber(customerDTO.getIdentityNumber())) {
                throw new IdentityNumberIsAlreadyExistException(customerDTO.getIdentityNumber() + "--> This id number already exists.");
            }
        }

        if(!toUpdateCustomer.getPhoneNumber().equals(customerDTO.getPhoneNumber())) {
            if(this.customerRepository.existsByIdentityNumber(customerDTO.getPhoneNumber())) {
                throw new PhoneNumberIsAlreadyExistException(customerDTO.getPhoneNumber() + "--> This phone number already exists.");
            }
        }

        toUpdateCustomer = this.customerMapper.fromCustomerDtoToCustomer(customerDTO);
        this.customerRepository.save(toUpdateCustomer);
        this.applicationService.update(toUpdateCustomer, this.customerRepository.findCustomerByApplicationId(toUpdateCustomer.getId()));
        log.info("ICustomerService: Application updated.");

        return this.customerMapper.fromCustomerToCustomerDto(toUpdateCustomer);
    }

    @Override
    public void delete(Long id) {
        this.customerRepository.deleteById(id);
    }

    @Override
    public List<CustomerDTO> getAll() {
        List<Customer> customers = this.customerRepository.findAll();
        return this.customerMapper.fromCustomerListToCustomerDtoList(customers);
    }

    @Override
    public CustomerDTO getOneCustomer(Long id) {
        Optional<Customer> optionalCustomer = this.customerRepository.findById(id);

        if(!optionalCustomer.isPresent()) {
            throw new NotFoundException("Customer with id " + id + " could not be found!");
        }
        Customer customer = optionalCustomer.get();

        return this.customerMapper.fromCustomerToCustomerDto(customer);
    }
}
