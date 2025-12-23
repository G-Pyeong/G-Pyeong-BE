package com.gpyeong.core.domain.auth.domain.repository;

import java.util.Optional;
import java.util.List;
import com.gpyeong.core.domain.auth.domain.entity.Gender;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gpyeong.core.domain.auth.domain.entity.OAuthProvider;
import com.gpyeong.core.domain.auth.domain.entity.User;

public interface UserRepository extends MongoRepository<User, Integer> {

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
    
    Boolean existsByUserId(Integer userId);
    
    Optional<User> findByUserId(Integer userId);

    List<User> findAllByGender(Gender gender);
    
    Optional<User> findByProviderIdAndProvider(String providerId, OAuthProvider provider);
}