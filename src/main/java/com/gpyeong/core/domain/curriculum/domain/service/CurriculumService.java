package com.gpyeong.core.domain.curriculum.domain.service;

import com.gpyeong.core.domain.curriculum.domain.entity.Section;
import com.gpyeong.core.domain.curriculum.domain.entity.Subject;
import com.gpyeong.core.domain.curriculum.domain.repository.SectionRepository;
import com.gpyeong.core.domain.curriculum.domain.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurriculumService {

    private final SubjectRepository subjectRepository;
    private final SectionRepository sectionRepository;

    public List<Subject> findAllSubjects() {
        return subjectRepository.findAll();
    }

    public List<Section> findSectionsBySubjectId(String subjectId) {
        return sectionRepository.findBySubjectId(subjectId);
    }
}
