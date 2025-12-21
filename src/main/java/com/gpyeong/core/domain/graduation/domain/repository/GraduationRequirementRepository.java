package com.gpyeong.core.domain.graduation.domain.repository;

import com.gpyeong.core.domain.graduation.domain.entity.GraduationRequirement;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GraduationRequirementRepository extends MongoRepository<GraduationRequirement, String> {
}
