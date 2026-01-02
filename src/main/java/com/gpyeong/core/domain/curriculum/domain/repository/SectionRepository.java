package com.gpyeong.core.domain.curriculum.domain.repository;

import com.gpyeong.core.domain.common.domain.entity.SemesterEnum;
import com.gpyeong.core.domain.curriculum.domain.entity.Section;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SectionRepository extends MongoRepository<Section, String> {
    List<Section> findBySubjectId(String subjectId);

    List<Section> findBySubjectIdIn(List<String> subjectIds);

    List<Section> findByYearIdAndSemester(String yearId, SemesterEnum semester);
}