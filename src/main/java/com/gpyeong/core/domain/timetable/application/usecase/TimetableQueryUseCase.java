package com.gpyeong.core.domain.timetable.application.usecase;

import com.gpyeong.core.domain.timetable.application.dto.response.TimetableResponse;
import com.gpyeong.core.domain.timetable.domain.service.TimetableReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TimetableQueryUseCase {

    private final TimetableReadService timetableReadService;

    public TimetableResponse getTimetable(String timetableId) {
        return timetableReadService.getTimetableDetail(timetableId);
    }
}
