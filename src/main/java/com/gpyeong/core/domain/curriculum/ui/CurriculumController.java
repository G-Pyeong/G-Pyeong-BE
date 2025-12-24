package com.gpyeong.core.domain.curriculum.ui;

import com.gpyeong.core.domain.curriculum.domain.entity.Section;
import com.gpyeong.core.domain.curriculum.domain.entity.Subject;
import com.gpyeong.core.domain.curriculum.domain.repository.SectionRepository;
import com.gpyeong.core.domain.curriculum.domain.repository.SubjectRepository;
import com.gpyeong.core.global.common.BaseResponse;
import com.gpyeong.core.global.swagger.CurriculumApi; // 추가
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/curriculum")
@RequiredArgsConstructor
public class CurriculumController implements CurriculumApi { // 인터페이스 구현 추가

    private final SubjectRepository subjectRepository;
    private final SectionRepository sectionRepository;

    @GetMapping("/subjects")
    @Override // 어노테이션 추가
    public BaseResponse<List<Subject>> getAllSubjects() {
        return BaseResponse.onSuccess(subjectRepository.findAll());
    }

    @GetMapping("/subjects/{subjectId}/sections")
    @Override // 어노테이션 추가
    public BaseResponse<List<Section>> getSectionsBySubject(@PathVariable String subjectId) {
        return BaseResponse.onSuccess(sectionRepository.findBySubjectId(subjectId));
    }
}