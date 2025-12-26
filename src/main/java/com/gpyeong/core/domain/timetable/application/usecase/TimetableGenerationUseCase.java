package com.gpyeong.core.domain.timetable.application.usecase;

import com.gpyeong.core.domain.curriculum.domain.entity.Section;
import com.gpyeong.core.domain.timetable.application.dto.request.TimetableGenerationRequest;
import com.gpyeong.core.domain.timetable.domain.entity.Timetable;
import com.gpyeong.core.domain.timetable.domain.entity.TimetableItem;
import com.gpyeong.core.domain.timetable.domain.repository.TimetableItemRepository;
import com.gpyeong.core.domain.timetable.domain.repository.TimetableRepository;
import com.gpyeong.core.domain.timetable.domain.service.TimetableGenerationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TimetableGenerationUseCase {

    private final TimetableRepository timetableRepository;
    private final TimetableItemRepository timetableItemRepository;
    private final TimetableGenerationService timetableGenerationService;

    @Transactional
    public String generate(Integer userId, TimetableGenerationRequest request) {

        List<Section> bestCombination = timetableGenerationService.generateAlgorithm(request);

        return saveTimetable(userId, request, bestCombination);
    }

    private String saveTimetable(Integer userId, TimetableGenerationRequest request, List<Section> sections) {

        int totalCredits = timetableGenerationService.calculateTotalCredits(sections);

        Timetable timetable = Timetable.builder()
                .userId(userId)
                .yearId(request.yearId())
                .semester(request.semester())
                .summary("자동 생성된 시간표")
                .totalCredit(totalCredits)
                .build();

        Timetable saved = timetableRepository.save(timetable);

        List<TimetableItem> items = sections.stream()
                .map(s -> TimetableItem.builder()
                        .timetableId(saved.getTimetableId())
                        .sectionId(s.getId())
                        .build())
                .collect(Collectors.toList());

        timetableItemRepository.saveAll(items);
        return saved.getTimetableId();
    }
}