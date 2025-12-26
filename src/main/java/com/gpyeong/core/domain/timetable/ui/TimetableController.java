package com.gpyeong.core.domain.timetable.ui;

import com.gpyeong.core.domain.auth.domain.entity.User;
import com.gpyeong.core.domain.timetable.application.dto.request.TimetableGenerationRequest;
import com.gpyeong.core.domain.timetable.application.usecase.TimetableGenerationUseCase;
import com.gpyeong.core.global.annotation.CurrentUser;
import com.gpyeong.core.global.common.BaseResponse;
import com.gpyeong.core.global.swagger.TimetableApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gpyeong.core.domain.timetable.application.dto.response.TimetableGenerationResponse;
import com.gpyeong.core.domain.timetable.application.dto.response.TimetableResponse;
import com.gpyeong.core.domain.timetable.application.usecase.TimetableQueryUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/timetables")
@RequiredArgsConstructor
public class TimetableController implements TimetableApi {

    private final TimetableGenerationUseCase timetableGenerationUseCase;
    private final TimetableQueryUseCase timetableQueryUseCase;

    @Override
    @PostMapping("/generate")
    public BaseResponse<TimetableGenerationResponse> generateTimetable(
            @CurrentUser User user,
            @RequestBody TimetableGenerationRequest request) {

        String timetableId = timetableGenerationUseCase.generate(user.getUserId(), request);

        return BaseResponse.onSuccess(TimetableGenerationResponse.of(timetableId));
    }

    @GetMapping("/{timetableId}")
    public BaseResponse<TimetableResponse> getTimetable(@PathVariable String timetableId) {
        TimetableResponse response = timetableQueryUseCase.getTimetable(timetableId);
        return BaseResponse.onSuccess(response);
    }
}