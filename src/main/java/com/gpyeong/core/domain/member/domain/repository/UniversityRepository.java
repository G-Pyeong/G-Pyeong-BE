package com.gpyeong.core.domain.member.domain.repository;

import com.gpyeong.core.domain.member.domain.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityRepository extends JpaRepository<University, Long> {
}
