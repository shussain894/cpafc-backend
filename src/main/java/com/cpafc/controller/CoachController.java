package com.cpafc.controller;

import com.cpafc.model.Coach;
import com.cpafc.repository.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/coaches")
public class CoachController {

    @Autowired
    private CoachRepository coachRepository;

    @GetMapping
    public List<Coach> getAllCoaches() {
        return coachRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Coach> getCoachById(@PathVariable String id) {
        return coachRepository.findById(id);
    }

    @PostMapping
    public ResponseEntity<?> createCoach(@RequestBody Coach coach) {
        Optional<Coach> existingCoach = coachRepository.findByFirstNameAndLastName(
                coach.getFirstName(), coach.getLastName()
        );
        if (existingCoach.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Coach '" + coach.getFirstName() + " " + coach.getLastName() + "' already exists.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(coachRepository.save(coach));
    }

    @PutMapping("/{id}")
    public Coach updateCoach(@PathVariable String id, @RequestBody Coach updatedCoach) {
        updatedCoach.setId(id);
        return coachRepository.save(updatedCoach);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCoach(@PathVariable String id) {
        if (coachRepository.existsById(id)) {
            coachRepository.deleteById(id);
            return ResponseEntity.ok("Coach deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Coach not found with ID: " + id);
        }
    }
}
