package com.gpyeong.core.domain.member.domain.repository;

import com.gpyeong.core.domain.member.domain.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
