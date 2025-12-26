package com.gpyeong.core.global.swagger;

import com.gpyeong.core.domain.auth.domain.entity.User;
import com.gpyeong.core.domain.timetable.application.dto.request.TimetableGenerationRequest;
import com.gpyeong.core.domain.timetable.application.dto.response.TimetableGenerationResponse;
import com.gpyeong.core.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "시간표 관리", description = "시간표 생성 및 조회 API")
public interface TimetableApi {

        @Operation(summary = "시간표 자동 생성", description = "사용자가 선택한 필수 과목과 선호 조건을 기반으로 최적의 시간표를 생성합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "생성 성공 (생성된 시간표 ID 반환)"),
                        @ApiResponse(responseCode = "400", description = "시간표 생성 실패 (조건 충돌)")
        })
        BaseResponse<TimetableGenerationResponse> generateTimetable(
                        @Parameter(hidden = true) User user,
                        @RequestBody TimetableGenerationRequest request);
}