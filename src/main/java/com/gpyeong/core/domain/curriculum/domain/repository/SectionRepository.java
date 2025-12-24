package com.gpyeong.core.domain.curriculum.domain.repository;

import com.gpyeong.core.domain.common.domain.entity.SemesterEnum;
import com.gpyeong.core.domain.curriculum.domain.entity.Section;
import java.util.List;
import java.util.Optional;

public interface SectionRepository {
    Section save(Section section);
    Optional<Section> findById(String id);
    List<Section> findBySubjectId(String subjectId);
    List<Section> findByYearIdAndSemester(String yearId, SemesterEnum semester);
    void deleteAll();
}