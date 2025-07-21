package com.cpafc.controller;

import com.cpafc.model.Coach;
import com.cpafc.model.Role;
import com.cpafc.repository.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private CoachRepository coachRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        Optional<Coach> optionalCoach = coachRepository.findByEmail(email);
        boolean isAdmin = optionalCoach.isPresent() && optionalCoach.get().getRole() == Role.ADMIN;

        Map<String, Object> response = new HashMap<>();
        response.put("email", email);
        response.put("isAdmin", isAdmin);

        return ResponseEntity.ok(response);
    }
}
