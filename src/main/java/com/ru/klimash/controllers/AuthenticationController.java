package com.ru.klimash.controllers;

import com.ru.klimash.services.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.apache.hc.core5.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/main_menu/authentication_api")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final String url_regret_auth;
    private final String url_ui;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService,
                                    @Value("${url_regret_auth}") String url_regret_auth,
                                    @Value("${url_ui}") String url_ui) {
        this.authenticationService = authenticationService;
        this.url_regret_auth = url_regret_auth;
        this.url_ui = url_ui;
    }

    @GetMapping("/authentication")
    public String authenticationForm() {
        return "authentication-form";
    }

    @GetMapping("/authentication_regret")
    public String authenticationRegret() {
        return "authentication-regret";
    }

    @Transactional
    @PostMapping("/authentication/check_customer")
    public ResponseEntity<String> authentication(@RequestParam String auth_email,
                               @RequestParam String auth_password,
                                 HttpServletResponse response) {
        try {
            ResponseEntity<String> authResponse = authenticationService.customerExists(auth_email, auth_password);

            String setCookieHeader = authResponse.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
            if (setCookieHeader != null) {
                response.addHeader(HttpHeaders.SET_COOKIE, setCookieHeader);
            }

            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, url_ui)
                    .build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .header(HttpHeaders.LOCATION, url_regret_auth)
                    .build();
        }





    }
}
