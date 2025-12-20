package com.gpyeong.core.domain.timetable.domain.repository;

import com.gpyeong.core.domain.timetable.domain.entity.Timetable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TimetableRepository extends MongoRepository<Timetable, String> {
}
