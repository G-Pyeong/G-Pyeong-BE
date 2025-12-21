package com.gpyeong.core.domain.graduation.domain.repository;

import com.gpyeong.core.domain.graduation.domain.entity.RequiredSubject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RequiredSubjectRepository extends MongoRepository<RequiredSubject, String> {
}
