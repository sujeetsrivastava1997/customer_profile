package com.example.customer.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.customer.service.CustomerService;
import com.example.customer.model.Customer;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService service;
    public CustomerController(CustomerService service) { this.service = service; }
    @PostMapping
    public Customer create(@RequestBody Customer customer) { return service.create(customer); }
    @GetMapping
    public List<Customer> getAll() { return service.getAll(); }
    @GetMapping("/search")
    public List<Customer> search(@RequestParam String keyword) { return service.search(keyword); }
}
