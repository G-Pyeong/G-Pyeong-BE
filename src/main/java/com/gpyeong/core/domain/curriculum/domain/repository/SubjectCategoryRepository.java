package com.gpyeong.core.domain.curriculum.domain.repository;

import com.gpyeong.core.domain.curriculum.domain.entity.SubjectCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubjectCategoryRepository extends MongoRepository<SubjectCategory, String> {
}
