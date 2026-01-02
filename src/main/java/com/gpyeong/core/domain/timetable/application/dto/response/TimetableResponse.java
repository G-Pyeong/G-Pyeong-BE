package com.gpyeong.core.domain.timetable.application.dto.response;

import com.gpyeong.core.domain.common.domain.entity.SemesterEnum;
import com.gpyeong.core.domain.timetable.domain.entity.Timetable;
import lombok.Builder;

import java.util.List;

@Builder
public record TimetableResponse(
        String timetableId,
        String yearId,
        SemesterEnum semester,
        int totalCredit,
        List<TimetableItemResponse> timetableItems) {
    public static TimetableResponse of(Timetable timetable, List<TimetableItemResponse> items) {
        return TimetableResponse.builder()
                .timetableId(timetable.getTimetableId())
                .yearId(timetable.getYearId())
                .semester(timetable.getSemester())
                .totalCredit(timetable.getTotalCredit())
                .timetableItems(items)
                .build();
    }
}
