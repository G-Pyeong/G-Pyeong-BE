package com.gpyeong.core.domain.auth.domain.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gpyeong.core.domain.auth.domain.entity.OAuthProvider;
import com.gpyeong.core.domain.auth.domain.entity.User;

public interface UserRepository extends MongoRepository<User, Integer> {

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
    
    Boolean existsByUserId(Integer userId);
    
    Optional<User> findByUserId(Integer userId);
    
    Optional<User> findByProviderIdAndProvider(String providerId, OAuthProvider provider);
}