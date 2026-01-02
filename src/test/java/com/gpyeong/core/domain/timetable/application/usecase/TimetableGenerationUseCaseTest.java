package com.gpyeong.core.domain.timetable.application.usecase;

import com.gpyeong.core.domain.common.domain.entity.SemesterEnum;
import com.gpyeong.core.domain.curriculum.domain.entity.Subject;
import com.gpyeong.core.domain.curriculum.domain.repository.SectionRepository;
import com.gpyeong.core.domain.curriculum.domain.repository.SubjectRepository;
import com.gpyeong.core.domain.timetable.application.dto.request.TimetableGenerationRequest;
import com.gpyeong.core.domain.timetable.domain.entity.Timetable;
import com.gpyeong.core.domain.timetable.domain.entity.TimetableItem;
import com.gpyeong.core.domain.timetable.domain.repository.TimetableItemRepository;
import com.gpyeong.core.domain.timetable.domain.repository.TimetableRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class TimetableGenerationUseCaseTest {

        @Autowired
        private TimetableGenerationUseCase timetableGenerationUseCase;

        // Service might be needed for direct testing later, but UseCase test covers
        // integration for now.

        @Autowired
        private SubjectRepository subjectRepository;

        @Autowired
        private TimetableRepository timetableRepository;

        @Autowired
        private TimetableItemRepository timetableItemRepository;

        @Autowired
        private SectionRepository sectionRepository;

        @Test
        void generateTimetable_ShouldFillElectives() {
                // Given
                // 1. 필수 과목 "알고리즘" (3학점) ID 조회
                Subject algo = subjectRepository.findAll().stream()
                                .filter(s -> "알고리즘".equals(s.getName()))
                                .findFirst()
                                .orElseThrow(() -> new IllegalStateException("알고리즘 과목이 시드 데이터에 없습니다."));

                // 2. 교양 카테고리 "CAT_HUMANITIES" (역사의 이해, 2학점)
                // 목표 학점: 5점 (알고리즘 3 + 교양 2)

                TimetableGenerationRequest request = new TimetableGenerationRequest(
                                "2024",
                                SemesterEnum.FIRST_SEMESTER,
                                5, // Target Credits
                                List.of(algo.getSubjectId()), // Required: Algorithm
                                List.of("CAT_HUMANITIES"), // Preferred Category
                                List.of(), // Preferred Day Offs
                                false // Avoid Morning
                );

                // When
                String timetableId = timetableGenerationUseCase.generate(1, request);

                // Then
                assertThat(timetableId).isNotNull();

                Timetable timetable = timetableRepository.findById(timetableId).orElseThrow();
                assertThat(timetable.getTotalCredit()).isGreaterThanOrEqualTo(5);
                assertThat(timetable.getSummary()).isEqualTo("자동 생성된 시간표");
        }

        @Test
        void generateTimetable_ShouldPreferCompactSchedule() {
                // Given
                // 1. 필수 과목: 알고리즘 (월, 수 15:00 ~ 16:30)
                Subject algo = subjectRepository.findAll().stream()
                                .filter(s -> "알고리즘".equals(s.getName()))
                                .findFirst()
                                .orElseThrow();

                // 2. 교양 과목 후보: 컴퓨터 사이언스 (운영체제)
                // - 분반 1 (01): 월, 수 09:00 ~ 10:30 (알고리즘과 긴 공강 발생)
                // - 분반 2 (02): 화, 목 13:30 ~ 15:00 (공강 없음, 가장 선호되어야 함)

                TimetableGenerationRequest request = new TimetableGenerationRequest(
                                "2024",
                                SemesterEnum.FIRST_SEMESTER,
                                6, // Target Credits (Algo 3 + OS 3)
                                List.of(algo.getSubjectId()),
                                List.of("CAT_COMPUTER_SCIENCE"),
                                List.of(), // No specific preference
                                false // "오전 수업 상관 없음" -> Compactness 가 중요해짐
                );

                // When
                String timetableId = timetableGenerationUseCase.generate(1, request);

                // Then
                assertThat(timetableId).isNotNull();

                // 생성된 시간표의 아이템 조회
                List<TimetableItem> items = timetableItemRepository.findByTimetableId(timetableId);
                List<String> sectionIds = items.stream().map(TimetableItem::getSectionId).toList();
                var sections = sectionRepository.findAllById(sectionIds);

                // "화요일" 수업이 포함되어 있는지 확인 (분반 2가 화/목이므로)
                boolean hasTueClass = sections.stream()
                                .flatMap(s -> s.getMeetingTimes().stream())
                                .anyMatch(mt -> mt
                                                .getDayOfWeek() == com.gpyeong.core.domain.curriculum.domain.entity.DayOfWeek.TUE);

                assertThat(hasTueClass).as("공강이 적은 화목 오후 분반(02)이 선택되어야 합니다.")
                                .isTrue();
        }
}
