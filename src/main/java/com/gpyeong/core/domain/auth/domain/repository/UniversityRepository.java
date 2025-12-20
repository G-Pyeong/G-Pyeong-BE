package com.gpyeong.core.domain.auth.domain.repository;

import com.gpyeong.core.domain.auth.domain.entity.University;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UniversityRepository extends MongoRepository<University, Long> {
}
