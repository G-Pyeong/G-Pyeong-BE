package com.gpyeong.core.domain.timetable.domain.repository;

import com.gpyeong.core.domain.timetable.domain.entity.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimetableRepository extends JpaRepository<Timetable, String> {
}
