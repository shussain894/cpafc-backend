package com.cpafc.repository;

import com.cpafc.model.Child;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildRepository extends MongoRepository<Child, String> {
}
