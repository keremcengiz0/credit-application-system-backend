package com.keremcengiz0.creditapplicationsystem.repositories;

import com.keremcengiz0.creditapplicationsystem.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
