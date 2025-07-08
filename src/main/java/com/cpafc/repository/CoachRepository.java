package com.cpafc.repository;

import com.cpafc.model.Coach;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CoachRepository extends MongoRepository<Coach, String> {
    List<Coach> findByTeam(String team);
}