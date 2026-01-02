package com.gpyeong.core.domain.timetable.application.dto.request;

import com.gpyeong.core.domain.common.domain.entity.SemesterEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record TimetableGenerationRequest(
        @Schema(description = "대상 연도 ID", example = "2025")
        String yearId,

        @Schema(description = "대상 학기", example = "FIRST_SEMESTER")
        SemesterEnum semester,

        @Schema(description = "목표 학점", example = "18")
        Integer targetCredits,

        @Schema(description = "필수 포함 과목 ID 목록", example = "[\"subject-id-1\"]")
        List<String> requiredSubjectIds,

        @Schema(description = "선호 교양 카테고리 ID 목록", example = "[\"category-id-1\"]")
        List<String> preferredCategoryIds,

        @Schema(description = "공강 희망 요일", example = "[\"FRI\"]")
        List<String> preferredDayOffs,

        @Schema(description = "1교시(09:00) 수업 회피 여부", example = "true")
        boolean avoidMorning
) {
    public TimetableGenerationRequest {
        if (targetCredits != null && targetCredits < 0) {
            throw new IllegalArgumentException("목표 학점은 0 이상이어야 합니다.");
        }
    }
}