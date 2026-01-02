package com.gpyeong.core.domain.curriculum.application.dto.response;

import com.gpyeong.core.domain.common.domain.entity.SemesterEnum;
import com.gpyeong.core.domain.curriculum.domain.entity.DayOfWeek;
import com.gpyeong.core.domain.curriculum.domain.entity.MeetingTime;
import com.gpyeong.core.domain.curriculum.domain.entity.Section;

import java.time.LocalDateTime;
import java.util.List;

public record SectionResponse(
        String id,
        String subjectId,
        String sectionNumber,
        String professor,
        String yearId,
        SemesterEnum semester,
        List<MeetingTimeResponse> meetingTimes) {
    public static SectionResponse from(Section section) {
        return new SectionResponse(
                section.getId(),
                section.getSubjectId(),
                section.getSectionNumber(),
                section.getProfessor(),
                section.getYearId(),
                section.getSemester(),
                section.getMeetingTimes().stream()
                        .map(MeetingTimeResponse::from)
                        .toList());
    }

    public record MeetingTimeResponse(
            DayOfWeek dayOfWeek,
            LocalDateTime startTime,
            LocalDateTime endTime) {
        public static MeetingTimeResponse from(MeetingTime meetingTime) {
            return new MeetingTimeResponse(
                    meetingTime.getDayOfWeek(),
                    meetingTime.getStartTime(),
                    meetingTime.getEndTime());
        }
    }
}
