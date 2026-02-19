package com.example.customer.service;

import org.springframework.stereotype.Service;
import java.util.List;
import com.example.customer.model.Customer;
import com.example.customer.repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer create(Customer customer) { return repository.save(customer); }
    public List<Customer> getAll() { return repository.findAll(); }

    public List<Customer> search(String keyword) {
        return repository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                keyword, keyword, keyword);
    }
}
