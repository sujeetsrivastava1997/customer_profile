package com.example.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.customer.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Search by firstName, lastName, or email
    List<Customer> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String firstName, String lastName, String email);
}
