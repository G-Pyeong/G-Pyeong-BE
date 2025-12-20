package com.gpyeong.core.domain.timetable.domain.repository;

import com.gpyeong.core.domain.timetable.domain.entity.TimetableItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TimetableItemRepository extends MongoRepository<TimetableItem, String> {
}
