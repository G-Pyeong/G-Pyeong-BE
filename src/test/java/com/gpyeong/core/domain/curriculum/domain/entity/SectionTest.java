package com.gpyeong.core.domain.curriculum.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

class SectionTest {

        @Test
        @DisplayName("서로 다른 요일에 개설된 분반은 충돌하지 않는다")
        void shouldNotConflictOnDifferentDays() {
                // given
                Section monSection = Section.builder()
                                .meetingTimes(List.of(MeetingTime.of(DayOfWeek.MON, LocalDateTime.of(2024, 3, 4, 9, 0),
                                                LocalDateTime.of(2024, 3, 4, 10, 30))))
                                .build();
                Section tueSection = Section.builder()
                                .meetingTimes(List.of(MeetingTime.of(DayOfWeek.TUE, LocalDateTime.of(2024, 3, 5, 9, 0),
                                                LocalDateTime.of(2024, 3, 5, 10, 30))))
                                .build();

                // when
                boolean isConflict = monSection.isConflict(tueSection);

                // then
                assertThat(isConflict).isFalse();
        }

        @Test
        @DisplayName("같은 요일에 시간이 겹치면 충돌로 판정한다")
        void shouldConflictOnOverlappingTime() {
                // given: 09:00~10:30 수업과 10:00~11:30 수업 (30분 중첩)
                Section section1 = Section.builder()
                                .meetingTimes(List.of(MeetingTime.of(DayOfWeek.FRI, LocalDateTime.of(2024, 3, 8, 9, 0),
                                                LocalDateTime.of(2024, 3, 8, 10, 30))))
                                .build();
                Section section2 = Section.builder()
                                .meetingTimes(List.of(MeetingTime.of(DayOfWeek.FRI, LocalDateTime.of(2024, 3, 8, 10, 0),
                                                LocalDateTime.of(2024, 3, 8, 11, 30))))
                                .build();

                // when & then
                assertThat(section1.isConflict(section2)).isTrue();
        }
}