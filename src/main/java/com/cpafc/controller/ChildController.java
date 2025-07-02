package com.cpafc.controller;

import com.cpafc.model.Child;
import com.cpafc.repository.ChildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/children")
public class ChildController {

    @Autowired
    private ChildRepository childRepository;

    // Get all children
    @GetMapping
    public List<Child> getAllChildren() {
        return childRepository.findAll();
    }

    // Get child by ID
    @GetMapping("/{id}")
    public Optional<Child> getChildById(@PathVariable String id) {
        return childRepository.findById(id);
    }

    // Add a new child
    @PostMapping
    public Child createChild(@RequestBody Child child) {
        System.out.println("Received child: " + child.getFirstName() + " " + child.getLastName());
        return childRepository.save(child);
    }

    // Update an existing child
    @PutMapping("/{id}")
    public Child updateChild(@PathVariable String id, @RequestBody Child updatedChild) {
        updatedChild.setId(id);
        return childRepository.save(updatedChild);
    }

    // Delete a child
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


//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleException(Exception e) {
//        e.printStackTrace();  // logs the error stack trace
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
//    }
}
