package com.gpyeong.core.domain.member.domain.repository;

import com.gpyeong.core.domain.member.domain.entity.University;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UniversityRepository extends MongoRepository<University, Long> {
}
