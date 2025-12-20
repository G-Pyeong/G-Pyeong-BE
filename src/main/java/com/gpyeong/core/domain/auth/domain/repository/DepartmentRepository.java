package com.gpyeong.core.domain.auth.domain.repository;

import com.gpyeong.core.domain.auth.domain.entity.Department;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DepartmentRepository extends MongoRepository<Department, Long> {
}
