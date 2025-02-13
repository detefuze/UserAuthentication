package com.ru.klimash.services;

import com.ru.klimash.dto.CustomerDTO;
import com.ru.klimash.entites.Customer;
import com.ru.klimash.repositories.CustomersRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.sql.Timestamp;

@Service
public class AuthenticationService {

    private static final Logger log = LogManager.getLogger(AuthenticationService.class);
    private final CustomersRepository customersRepository;

    private final RestTemplate restTemplate;

    @Autowired
    public AuthenticationService(CustomersRepository customersRepository,
                                 RestTemplate restTemplate) {
        this.customersRepository = customersRepository;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public boolean customerExists(String email, String password) throws RuntimeException {

        Customer customer = customersRepository.findByEmailAndPassword(email, password);

        try {
            if (customer == null) throw new RuntimeException("User is not found");
            customer.setAuthenticated_at(new Timestamp(System.currentTimeMillis()));

            CustomerDTO customerDTO = new CustomerDTO();

            customerDTO.setId(customer.getId());
            customerDTO.setFio(customer.getFio());
            customerDTO.setEmail(customer.getEmail());
            customerDTO.setBalance(customer.getBalance());

            // Создаем заголовок, передаем данные в формате JSON
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<CustomerDTO> response = new HttpEntity<>(customerDTO, headers);

            String uiServiceUrl = "http://userinterface:8080/main_menu";

            ResponseEntity<String> authenticationResponse = restTemplate.postForEntity(uiServiceUrl,
                    response,
                    String.class);

            return true;
        } catch (RuntimeException e) {
            log.error("e: ", e);
        }

        return false;
    }
}