package com.keremcengiz0.creditapplicationsystem.repositories;

import com.keremcengiz0.creditapplicationsystem.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByIdentityNumber(String identityNumber);
    boolean existsByPhoneNumber(String phoneNumber);

    @Query(value = "Select a.id from applications as a "
           + "where a.customer_id =:id", nativeQuery = true)
    Long findCustomerByApplicationId(Long id);

    Optional<Customer> findCustomerByIdentityNumber(String identityNumber);
}
