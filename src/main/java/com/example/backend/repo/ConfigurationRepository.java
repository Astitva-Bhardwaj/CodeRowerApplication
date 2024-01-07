package com.example.backend.repo;

import com.example.backend.model.ConfigurationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ConfigurationRepository extends MongoRepository<ConfigurationEntity, String> {

    Optional<ConfigurationEntity> findById(String id);

}
