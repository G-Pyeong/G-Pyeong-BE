package com.gpyeong.core.domain.curriculum.infra.repository;

import com.gpyeong.core.domain.common.domain.entity.SemesterEnum;
import com.gpyeong.core.domain.curriculum.domain.entity.Section;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface MongoSectionRepository extends MongoRepository<Section, String> {
    List<Section> findBySubjectId(String subjectId);

    List<Section> findByYearIdAndSemester(String yearId, SemesterEnum semester);
}