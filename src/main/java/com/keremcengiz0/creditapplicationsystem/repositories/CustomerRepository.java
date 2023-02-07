package com.keremcengiz0.creditapplicationsystem.repositories;

import com.keremcengiz0.creditapplicationsystem.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByIdentityNumber(String identityNumber);
    boolean existsByPhoneNumber(String phoneNumber);

    //@Query(value = "Select a.id from applications as a "
           // + "where a.customer.id =:id" )
    Long findCustomerByApplicationId(Long id);
}
