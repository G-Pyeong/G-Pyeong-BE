package com.gpyeong.core.domain.curriculum.application.usecase;

import com.gpyeong.core.domain.curriculum.application.dto.response.SectionResponse;
import com.gpyeong.core.domain.curriculum.application.dto.response.SubjectResponse;
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

    public List<SubjectResponse> getAllSubjects() {
        return curriculumService.findAllSubjects().stream()
                .map(SubjectResponse::from)
                .toList();
    }

    public List<SectionResponse> getSectionsBySubject(String subjectId) {
        return curriculumService.findSectionsBySubjectId(subjectId).stream()
                .map(SectionResponse::from)
                .toList();
    }
}
