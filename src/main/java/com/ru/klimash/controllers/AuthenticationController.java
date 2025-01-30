package com.ru.klimash.controllers;

import com.ru.klimash.services.AuthenticationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/main_menu/authentication_api")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/authentication")
    public String authenticationForm() {
        return "authentication-form";
    }

    @GetMapping("/authentication_success")
    public String authenticationSuccess() {
        return "authentication-success";
    }

    @GetMapping("/authentication_regret")
    public String authenticationRegret() {
        return "authentication-regret";
    }

    @Transactional
    @PostMapping("/authentication/check_customer")
    public String authentication(@RequestParam String auth_email,
                               @RequestParam String auth_password) {

        if (!authenticationService.customerExists(auth_email, auth_password)) {
            return "redirect:http://localhost:8082/main_menu/authentication_api" +
                    "/authentication_regret";
        }

        return "redirect:http://localhost:8082/main_menu/authentication_api" +
                "/authentication_success";
    }
}
