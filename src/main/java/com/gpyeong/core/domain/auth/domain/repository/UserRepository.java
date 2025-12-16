package com.gpyeong.core.domain.auth.domain.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gpyeong.core.domain.auth.domain.entity.User;

public interface UserRepository extends MongoRepository<User, String> {

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
    
    Boolean existsByUserId(String userId);
    
    Optional<User> findByUserId(String userId);
}