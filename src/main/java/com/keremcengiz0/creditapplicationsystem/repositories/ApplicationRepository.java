package com.keremcengiz0.creditapplicationsystem.repositories;

import com.keremcengiz0.creditapplicationsystem.entities.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Application findByCustomerIdentityNumber(String identityNumber);
}
