package com.gpyeong.core.domain.graduation.domain.repository;

import com.gpyeong.core.domain.graduation.domain.entity.GraduationRequirement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GraduationRequirementRepository extends JpaRepository<GraduationRequirement, Long> {
}
