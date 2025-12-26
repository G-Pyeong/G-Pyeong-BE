package com.gpyeong.core.domain.timetable.application.dto.response;

import com.gpyeong.core.domain.curriculum.domain.entity.DayOfWeek;
import com.gpyeong.core.domain.curriculum.domain.entity.MeetingTime;
import com.gpyeong.core.domain.curriculum.domain.entity.Section;
import com.gpyeong.core.domain.curriculum.domain.entity.Subject;
import lombok.Builder;

import java.time.LocalTime;
import java.util.List;

@Builder
public record TimetableItemResponse(
        String subjectName,
        String professor,
        String sectionNumber,
        List<MeetingTimeDto> meetingTimes) {
    public static TimetableItemResponse of(Subject subject, Section section) {
        return TimetableItemResponse.builder()
                .subjectName(subject.getName())
                .professor(section.getProfessor())
                .sectionNumber(section.getSectionNumber())
                .meetingTimes(section.getMeetingTimes().stream()
                        .map(MeetingTimeDto::of)
                        .toList())
                .build();
    }

    @Builder
    public record MeetingTimeDto(
            DayOfWeek dayOfWeek,
            LocalTime startTime,
            LocalTime endTime) {
        public static MeetingTimeDto of(MeetingTime meetingTime) {
            return MeetingTimeDto.builder()
                    .dayOfWeek(meetingTime.getDayOfWeek())
                    .startTime(meetingTime.getStartTime())
                    .endTime(meetingTime.getEndTime())
                    .build();
        }
    }
}
