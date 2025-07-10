package com.ru.klimash.services;

import com.ru.klimash.dto.CustomerDTO;
import com.ru.klimash.entities.Customer;
import com.ru.klimash.repositories.CustomersRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class AuthenticationService {

    private static final Logger log = LogManager.getLogger(AuthenticationService.class);
    private final CustomersRepository customersRepository;
    private final RestTemplate restTemplate;
    private final SecretKey jwtKey;
    private final String url_ui;

    @Autowired
    public AuthenticationService(CustomersRepository customersRepository,
                                 RestTemplate restTemplate,
                                 @Value("${jwt.secret}") String key,
                                 @Value("$url_ui") String url_ui) {
        this.customersRepository = customersRepository;
        this.restTemplate = restTemplate;
        this.jwtKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        this.url_ui = url_ui;
    }

    @Transactional
    public ResponseEntity<String> customerExists(String email, String password) throws RuntimeException {

        Customer customer = customersRepository.findByEmailAndPassword(email, password);

        try {
            if (customer == null) throw new RuntimeException("User is not found");
            customer.setAuthenticated_at(new Timestamp(System.currentTimeMillis()));

            // Генерация JWT токена
            String jwtToken = Jwts.builder()
                    .subject(Integer.toString(customer.getId()))
                    .issuedAt(new Date())
                    .signWith(jwtKey)
                    .compact();

            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(customer.getId());
            customerDTO.setFio(customer.getFio());
            customerDTO.setEmail(customer.getEmail());
            customerDTO.setBalance(customer.getBalance());

            // Создаем заголовок, передаем данные в формате JSON
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE,
                    "customerToken=" + jwtToken +
                            "; Path=/" +
                            "; HttpOnly" +
                            "; SameSite=Lax");
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<CustomerDTO> response = new HttpEntity<>(customerDTO, headers);

            ResponseEntity<String> authenticationResponse = restTemplate.postForEntity(url_ui,
                    response,
                    String.class);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body("Authentication successful");
        } catch (RuntimeException e) {
            log.error("e: ", e);
        }

        return null;
    }
}