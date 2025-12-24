package com.gpyeong.core.domain.curriculum.ui;

import com.gpyeong.core.domain.curriculum.application.usecase.CurriculumUseCase;
import com.gpyeong.core.domain.curriculum.domain.entity.Section;
import com.gpyeong.core.domain.curriculum.domain.entity.Subject;
import com.gpyeong.core.global.common.BaseResponse;
import com.gpyeong.core.global.swagger.CurriculumApi; // 추가
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/curriculum")
@RequiredArgsConstructor
public class CurriculumController implements CurriculumApi {

    private final CurriculumUseCase curriculumUseCase;

    @GetMapping("/subjects")
    @Override
    public BaseResponse<List<Subject>> getAllSubjects() {
        return BaseResponse.onSuccess(curriculumUseCase.getAllSubjects());
    }

    @GetMapping("/subjects/{subjectId}/sections")
    @Override
    public BaseResponse<List<Section>> getSectionsBySubject(@PathVariable String subjectId) {
        return BaseResponse.onSuccess(curriculumUseCase.getSectionsBySubject(subjectId));
    }
}