package com.apigateway.ApiGateway.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apigateway.ApiGateway.service.KeycloakAuthService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final KeycloakAuthService authService;

    public AuthController(KeycloakAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Mono<String> login(@RequestParam String user, @RequestParam String pass) {
        return authService.login(user, pass);
    }


    
}
