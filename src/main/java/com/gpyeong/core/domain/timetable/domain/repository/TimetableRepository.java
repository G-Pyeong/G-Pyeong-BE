package com.gpyeong.core.domain.timetable.domain.repository;

import com.gpyeong.core.domain.timetable.domain.entity.Timetable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TimetableRepository extends MongoRepository<Timetable, String> {
    List<Timetable> findByUserId(Integer userId);
}
