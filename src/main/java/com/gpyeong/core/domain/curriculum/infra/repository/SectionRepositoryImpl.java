package com.gpyeong.core.domain.curriculum.infra.repository;

import com.gpyeong.core.domain.common.domain.entity.SemesterEnum;
import com.gpyeong.core.domain.curriculum.domain.entity.Section;
import com.gpyeong.core.domain.curriculum.domain.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SectionRepositoryImpl implements SectionRepository {
    private final MongoSectionRepository mongoSectionRepository;

    @Override
    public Section save(Section section) {
        return mongoSectionRepository.save(section);
    }

    @Override
    public Optional<Section> findById(String id) {
        return mongoSectionRepository.findById(id);
    }

    @Override
    public List<Section> findBySubjectId(String subjectId) {
        return mongoSectionRepository.findBySubjectId(subjectId);
    }

    @Override
    public List<Section> findByYearIdAndSemester(String yearId, SemesterEnum semester) {
        return mongoSectionRepository.findByYearIdAndSemester(yearId, semester);
    }

    @Override
    public void deleteAll() {
        mongoSectionRepository.deleteAll();
    }
}