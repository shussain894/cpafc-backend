package com.cpafc.controller;

import com.cpafc.model.Child;
import com.cpafc.repository.ChildRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void deleteChild(@PathVariable String id) {
        childRepository.deleteById(id);
    }
}
