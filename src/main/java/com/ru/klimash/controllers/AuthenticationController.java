package com.ru.klimash.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main_menu/authentication_api")
public class AuthenticationController {

    @GetMapping("/authentication")
    public String authenticationForm() {
        return "authentication-form";
    }
}
