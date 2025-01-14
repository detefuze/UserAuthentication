package com.ru.klimash.repositories;

import com.ru.klimash.entites.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomersRepository extends JpaRepository<Customer, Integer> {
    boolean existsByEmailAndPassword(String email, String password);
    Customer findByEmailAndPassword(String email, String password);
}
