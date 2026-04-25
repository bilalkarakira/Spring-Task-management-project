package com.bilal.taskmanager.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bilal.taskmanager.dto.AuthRequest;
import com.bilal.taskmanager.service.AuthService;
import com.bilal.taskmanager.util.AuthRequestParser;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final AuthRequestParser parser;

    public AuthController(AuthService authService) {
        this.authService = authService;
        this.parser = new AuthRequestParser(new com.fasterxml.jackson.databind.ObjectMapper());
    }
    @PostMapping("/signup")
    public Map<String, String> signup(@RequestHeader(value = "Content-Type", required = false) String contentType,
            @RequestBody(required = false) String body) {
        AuthRequest request = parser.parse(contentType, body);
        return Map.of("token", authService.signup(request.getEmail(), request.getPassword()));
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestHeader(value = "Content-Type", required = false) String contentType,
            @RequestBody(required = false) String body) {
        AuthRequest request = parser.parse(contentType, body);
        return Map.of("token", authService.login(request.getEmail(), request.getPassword()));
    }
}
