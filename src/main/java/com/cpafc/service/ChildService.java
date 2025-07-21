package com.cpafc.service;

import com.cpafc.model.Child;
import com.cpafc.model.Coach;
import com.cpafc.model.Role;
import com.cpafc.repository.ChildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class ChildService {

    @Autowired
    private ChildRepository childRepository;

    public List<Child> getAllChildren(Coach currentCoach) {
        if (currentCoach.getRole() == Role.ADMIN) {
            return childRepository.findAll();
        } else {
            return childRepository.findByTeam(currentCoach.getTeam());
        }
    }

    public Child getChildById(String id, Coach currentCoach) {
        Child child = childRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Child not found with ID: " + id));

        if (currentCoach.getRole() != Role.ADMIN && !currentCoach.getTeam().equals(child.getTeam())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied: not your team");
        }

        return child;
    }

    public Child createChild(Child child, Coach currentCoach) {
        if (currentCoach.getRole() != Role.ADMIN && !currentCoach.getTeam().equals(child.getTeam())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied: cannot create child for other teams");
        }
        return childRepository.save(child);
    }

    public Child updateChild(String id, Child updatedChild, Coach currentCoach) {
        Child existingChild = getChildById(id, currentCoach);
        updatedChild.setId(existingChild.getId());

        if (currentCoach.getRole() != Role.ADMIN && !currentCoach.getTeam().equals(updatedChild.getTeam())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied: cannot assign child to other teams");
        }

        return childRepository.save(updatedChild);
    }

    public void deleteChild(String id, Coach currentCoach) {
        Child child = getChildById(id, currentCoach);

        if (currentCoach.getRole() != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied: only admins can delete children");
        }

        childRepository.delete(child);
    }

    public Child assignChildToTeam(String id, String teamName, Coach currentCoach) {
        Child child = getChildById(id, currentCoach);

        if (currentCoach.getRole() != Role.ADMIN && !currentCoach.getTeam().equals(teamName)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied: cannot assign child to another team");
        }

        child.setTeam(teamName);
        return childRepository.save(child);
    }
}
