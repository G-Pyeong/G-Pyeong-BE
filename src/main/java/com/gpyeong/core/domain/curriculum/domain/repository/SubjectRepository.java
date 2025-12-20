package com.gpyeong.core.domain.curriculum.domain.repository;

import com.gpyeong.core.domain.curriculum.domain.entity.Subject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubjectRepository extends MongoRepository<Subject, Long> {
}
