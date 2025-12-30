package com.gpyeong.core.domain.timetable.application.usecase;

import com.gpyeong.core.domain.curriculum.domain.entity.Section;
import com.gpyeong.core.domain.timetable.application.dto.request.TimetableGenerationRequest;
import com.gpyeong.core.domain.timetable.domain.service.TimetableGenerationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TimetableGenerationUseCase {

    private final TimetableGenerationService timetableGenerationService;

    @Transactional
    public String generate(Integer userId, TimetableGenerationRequest request) {

        List<Section> bestCombination = timetableGenerationService.generateAlgorithm(request);

        return timetableGenerationService.saveTimetable(userId, request, bestCombination);
    }
}