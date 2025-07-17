package com.cpafc.controller;

import com.cpafc.model.Admin;
import com.cpafc.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @PostMapping("/grant")
    public ResponseEntity<String> grantAdmin(@RequestBody Admin admin) {
        Optional<Admin> existing = adminRepository.findByEmail(admin.getEmail());
        if (existing.isPresent()) {
            return ResponseEntity.ok("Admin access already granted to: " + admin.getEmail());
        }

        adminRepository.save(admin);
        return ResponseEntity.ok("Admin access granted to: " + admin.getEmail());
    }

    @DeleteMapping("/revoke")
    public ResponseEntity<String> revokeAdmin(@RequestBody Admin admin) {
        Optional<Admin> existing = adminRepository.findByEmail(admin.getEmail());
        if (existing.isPresent()) {
            adminRepository.delete(existing.get());
            return ResponseEntity.ok("Admin access revoked from: " + admin.getEmail());
        } else {
            return ResponseEntity.badRequest().body("No admin found with email: " + admin.getEmail());
        }
    }

    @GetMapping("/check")
    public String checkAdminAccess() {
        return "You are an admin!";
    }
}
