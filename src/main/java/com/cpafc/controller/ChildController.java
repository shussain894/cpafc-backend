package com.cpafc.controller;

import com.cpafc.model.Child;
import com.cpafc.repository.ChildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/api/children")
public class ChildController {

    @Autowired
    private ChildRepository childRepository;

    @GetMapping
    public List<Child> getAllChildren() {
        return childRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Child> getChildById(@PathVariable String id) {
        return childRepository.findById(id);
    }

    @PostMapping
    public Child createChild(@RequestBody Child child) {
        System.out.println("Received child: " + child.getFirstName() + " " + child.getLastName());
        return childRepository.save(child);
    }

    @PutMapping("/{id}")
    public Child updateChild(@PathVariable String id, @RequestBody Child updatedChild) {
        updatedChild.setId(id);
        return childRepository.save(updatedChild);
    }

    @PutMapping("/{id}/assign-team")
    public ResponseEntity<String> assignChildToTeam(@PathVariable String id, @RequestBody Map<String, String> request) {
        String teamName = request.get("team");
        Optional<Child> optionalChild = childRepository.findById(id);

        if (optionalChild.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Child not found");
        }

        Child child = optionalChild.get();
        child.setTeam(teamName);
        childRepository.save(child);

        return ResponseEntity.ok("Child assigned to team: " + teamName);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChild(@PathVariable String id) {
        if (childRepository.existsById(id)) {
            childRepository.deleteById(id);
            return ResponseEntity.ok("Child deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Child not found with ID: " + id);
        }
    }
}