package com.gpyeong.core.domain.auth.application.dto.request;

import com.gpyeong.core.domain.common.domain.entity.GradeEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignUpRequest(
        @NotBlank(message = "이름은 필수입니다.")
        String name,

        @NotBlank(message = "학과(부)는 필수입니다.")
        String department,

        @NotNull(message = "대학 ID는 필수입니다.")
        Integer universityId,

        @NotNull(message = "입학년도는 필수입니다.")
        @Min(value = 2021, message = "2021년도 이후 입학생만 가입 가능합니다.")
        Integer yearId,

        @NotNull(message = "학년은 필수입니다.")
        GradeEnum grade
) {}
