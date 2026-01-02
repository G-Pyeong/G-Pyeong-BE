package com.gpyeong.core.domain.timetable.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record TimetableGenerationResponse(
        @Schema(description = "생성된 시간표 ID", example = "64a1b2c...")
        String timetableId,

        @Schema(description = "결과 메시지", example = "시간표가 성공적으로 생성되었습니다.")
        String message
) {
    public static TimetableGenerationResponse of(String timetableId) {
        return new TimetableGenerationResponse(timetableId, "시간표가 성공적으로 생성되었습니다.");
    }
}