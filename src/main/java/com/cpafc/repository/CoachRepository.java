package com.cpafc.repository;

import com.cpafc.model.Coach;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

import java.util.List;

public interface CoachRepository extends MongoRepository<Coach, String> {
    List<Coach> findByTeam(String team);
    Optional<Coach> findByFirstNameAndLastName(String firstName, String lastName);
    Optional<Coach> findByEmail(String email);
}