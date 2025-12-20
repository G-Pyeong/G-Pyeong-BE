package com.gpyeong.core.domain.graduation.domain.repository;

import com.gpyeong.core.domain.graduation.domain.entity.RequiredSubject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequiredSubjectRepository extends JpaRepository<RequiredSubject, String> {
}
