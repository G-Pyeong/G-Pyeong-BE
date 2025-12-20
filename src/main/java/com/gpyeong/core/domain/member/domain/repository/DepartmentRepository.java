package com.gpyeong.core.domain.member.domain.repository;

import com.gpyeong.core.domain.member.domain.entity.Department;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DepartmentRepository extends MongoRepository<Department, Long> {
}
