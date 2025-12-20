package com.gpyeong.core.domain.timetable.domain.repository;

import com.gpyeong.core.domain.timetable.domain.entity.TimetableItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimetableItemRepository extends JpaRepository<TimetableItem, String> {
}
