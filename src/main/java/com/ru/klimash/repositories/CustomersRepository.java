package com.ru.klimash.repositories;

import com.ru.klimash.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomersRepository extends JpaRepository<Customer, Integer> {
    boolean existsByEmailAndPassword(String email, String password);
    Customer findByEmailAndPassword(String email, String password);
}
