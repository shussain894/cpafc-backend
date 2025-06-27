package com.cpafc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/api/health")
    public String healthCheck() {
        System.out.println("Health check called");
        return "API is up and running!";
    }
}
