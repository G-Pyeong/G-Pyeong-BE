package com.gpyeong.core.domain.common.domain.repository;

import com.gpyeong.core.domain.common.domain.entity.Year;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface YearRepository extends MongoRepository<Year, String> {
}
