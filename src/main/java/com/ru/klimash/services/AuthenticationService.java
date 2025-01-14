package com.ru.klimash.services;

import com.ru.klimash.entites.Customer;
import com.ru.klimash.repositories.CustomersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class AuthenticationService {

    private final CustomersRepository customersRepository;

    @Autowired
    public AuthenticationService(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    @Transactional
    public boolean customerExists(String email, String password) throws RuntimeException {

        Customer customer = customersRepository.findByEmailAndPassword(email, password);

        try {
            if (customer == null) throw new RuntimeException("User is not found");

            customer.setAuthenticated_at(new Timestamp(System.currentTimeMillis()));

            return true;
        } catch (RuntimeException e) {
            System.out.println("User is not found");
        }

        return false;
    }
}