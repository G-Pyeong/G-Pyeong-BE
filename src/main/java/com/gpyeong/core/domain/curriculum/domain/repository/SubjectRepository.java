package com.gpyeong.core.domain.curriculum.domain.repository;

import com.gpyeong.core.domain.curriculum.domain.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
