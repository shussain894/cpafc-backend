package com.cpafc.controller;

import com.cpafc.model.Admin;
import com.cpafc.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private AdminRepository adminRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        boolean isAdmin = adminRepository.findByEmail(email).isPresent();

        Map<String, Object> response = new HashMap<>();
        response.put("email", email);
        response.put("isAdmin", isAdmin);

        return ResponseEntity.ok(response);
    }
}
