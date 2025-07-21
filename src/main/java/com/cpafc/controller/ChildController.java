package com.cpafc.controller;

import com.cpafc.model.Child;
import com.cpafc.model.Coach;
import com.cpafc.service.ChildService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.cpafc.exception.ResourceNotFoundException;
import com.cpafc.exception.AccessDeniedException;

import java.util.List;

@RestController
@RequestMapping("/api/children")
public class ChildController {

    @Autowired
    private ChildService childService;

    @GetMapping
    public List<Child> getAllChildren(HttpServletRequest request) {
        Coach currentCoach = (Coach) request.getAttribute("currentCoach");
        return childService.getAllChildren(currentCoach);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Child> getChildById(@PathVariable String id, HttpServletRequest request) {
        Coach currentCoach = (Coach) request.getAttribute("currentCoach");
        try {
            Child child = childService.getChildById(id, currentCoach);
            return ResponseEntity.ok(child);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Child> createChild(@RequestBody Child child, HttpServletRequest request) {
        Coach currentCoach = (Coach) request.getAttribute("currentCoach");
        try {
            Child savedChild = childService.createChild(child, currentCoach);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedChild);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Child> updateChild(@PathVariable String id, @RequestBody Child updatedChild, HttpServletRequest request) {
        Coach currentCoach = (Coach) request.getAttribute("currentCoach");
        try {
            Child savedChild = childService.updateChild(id, updatedChild, currentCoach);
            return ResponseEntity.ok(savedChild);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChild(@PathVariable String id, HttpServletRequest request) {
        Coach currentCoach = (Coach) request.getAttribute("currentCoach");
        try {
            childService.deleteChild(id, currentCoach);
            return ResponseEntity.ok("Child deleted successfully");
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
