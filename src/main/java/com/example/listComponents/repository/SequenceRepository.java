package com.example.listComponents.repository;

import com.example.listComponents.model.SequenceCounter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceRepository extends MongoRepository<SequenceCounter, Long> {
}
