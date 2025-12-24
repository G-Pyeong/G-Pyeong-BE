package com.gpyeong.core.domain.curriculum.application.usecase;

import com.gpyeong.core.domain.curriculum.domain.entity.Section;
import com.gpyeong.core.domain.curriculum.domain.entity.Subject;
import com.gpyeong.core.domain.curriculum.domain.service.CurriculumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CurriculumUseCase {

    private final CurriculumService curriculumService;

    public List<Subject> getAllSubjects() {
        return curriculumService.findAllSubjects();
    }

    public List<Section> getSectionsBySubject(String subjectId) {
        return curriculumService.findSectionsBySubjectId(subjectId);
    }
}
