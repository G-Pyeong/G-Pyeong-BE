package com.gpyeong.core.domain.timetable.domain.service;

import com.gpyeong.core.domain.curriculum.domain.entity.Section;
import com.gpyeong.core.domain.curriculum.domain.entity.Subject;
import com.gpyeong.core.domain.curriculum.domain.repository.SectionRepository;
import com.gpyeong.core.domain.curriculum.domain.repository.SubjectRepository;
import com.gpyeong.core.domain.timetable.application.dto.response.TimetableItemResponse;
import com.gpyeong.core.domain.timetable.application.dto.response.TimetableResponse;
import com.gpyeong.core.domain.timetable.domain.entity.Timetable;
import com.gpyeong.core.domain.timetable.domain.entity.TimetableItem;
import com.gpyeong.core.domain.timetable.domain.repository.TimetableItemRepository;
import com.gpyeong.core.domain.timetable.domain.repository.TimetableRepository;
import com.gpyeong.core.global.exception.RestApiException;
import com.gpyeong.core.global.exception.code.status.GlobalErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TimetableReadService {

    private final TimetableRepository timetableRepository;
    private final TimetableItemRepository timetableItemRepository;
    private final SectionRepository sectionRepository;
    private final SubjectRepository subjectRepository;

    public TimetableResponse getTimetableDetail(String timetableId) {
        // Timetable 조회
        Timetable timetable = timetableRepository.findById(timetableId)
                .orElseThrow(() -> new RestApiException(GlobalErrorStatus._BAD_REQUEST));

        // Timetable Items 조회
        List<TimetableItem> items = timetableItemRepository.findByTimetableId(timetableId);

        // Section IDs 추출 및 조회
        List<String> sectionIds = items.stream()
                .map(TimetableItem::getSectionId)
                .toList();
        List<Section> sections = (List<Section>) sectionRepository.findAllById(sectionIds);

        Map<String, Section> sectionMap = sections.stream()
                .collect(Collectors.toMap(Section::getId, Function.identity()));

        // Subject IDs 추출 및 조회
        List<String> subjectIds = sections.stream()
                .map(Section::getSubjectId)
                .distinct()
                .toList();
        List<Subject> subjects = (List<Subject>) subjectRepository.findAllById(subjectIds);
        Map<String, Subject> subjectMap = subjects.stream()
                .collect(Collectors.toMap(Subject::getSubjectId, Function.identity()));

        List<TimetableItemResponse> itemResponses = items.stream()
                .map(item -> {
                    Section section = sectionMap.get(item.getSectionId());
                    Subject subject = subjectMap.get(item.getSubjectId());
                    // 데이터 무결성 체크
                    if (section == null || subject == null)
                        return null;
                    return TimetableItemResponse.of(subject, section);
                })
                .filter(res -> res != null)
                .toList();

        return TimetableResponse.of(timetable, itemResponses);
    }
}
