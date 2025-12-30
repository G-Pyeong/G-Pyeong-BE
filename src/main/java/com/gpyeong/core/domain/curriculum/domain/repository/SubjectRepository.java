package com.gpyeong.core.domain.curriculum.domain.repository;

import com.gpyeong.core.domain.curriculum.domain.entity.Subject;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface SubjectRepository extends MongoRepository<Subject, String> {
    List<Subject> findByCategoryIdIn(java.util.List<String> categoryIds);
}
