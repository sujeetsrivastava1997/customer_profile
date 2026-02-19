package com.example.customer;

import com.example.customer.model.Customer;
import com.example.customer.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CustomerProfileApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerProfileApplication.class, args);
    }
    @Bean
    CommandLineRunner initDatabase(CustomerRepository repository) {
        return args -> {
            repository.save(new Customer("John", "Doe", "john@example.com", "123 Street, NY", "1234567890", "English", true));
            repository.save(new Customer("Jane", "Smith", "jane@example.com", "456 Avenue, LA", "9876543210", "French", false));
            repository.save(new Customer("Alice", "Johnson", "alice@test.com", "789 Road, SF", "5555555555", "Spanish", true));
        };
    }
}

