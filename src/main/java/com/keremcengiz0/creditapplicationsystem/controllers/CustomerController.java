package com.keremcengiz0.creditapplicationsystem.controllers;

import com.keremcengiz0.creditapplicationsystem.dtos.CustomerDTO;
import com.keremcengiz0.creditapplicationsystem.services.abstracts.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> save(@RequestBody @Valid CustomerDTO customerDTO) {
        log.info("CustomerController: A request has been received to create a customer. ");
        return new ResponseEntity<>(this.customerService.save(customerDTO), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> update(@RequestBody @Valid CustomerDTO customerDTO, @PathVariable(name = "id") Long id) {
        customerDTO.setId(id);
        log.info("CustomerController: A request has been received to update a customer. ");
        return new ResponseEntity<>(this.customerService.update(customerDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        log.info("CustomerController: A request has been received to delete one customer by id. ");
        this.customerService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAll() {
        log.info("CustomerController: A request has been received to list all customers. ");
        return new ResponseEntity<>(this.customerService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CustomerDTO> get(@PathVariable(name = "id") Long id) {
        log.info("CustomerController: A request has been received to list one customer by id. ");
        return new ResponseEntity<>(this.customerService.get(id), HttpStatus.OK);
    }
}
