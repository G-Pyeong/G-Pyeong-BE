package com.gpyeong.core.domain.auth.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignUpRequest(
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        String email,

        @NotBlank(message = "아이디는 필수입니다.")
        String userId,

        @NotBlank(message = "비밀번호는 필수입니다.")
        @NotBlank String password,

        @NotBlank(message = "이름은 필수입니다.")
        @NotBlank String name,

        @NotBlank(message = "학과(부)는 필수입니다.")
        String department,

        @NotNull(message = "대학 ID는 필수입니다.")
        Integer universityId,

        @NotNull(message = "입학년도는 필수입니다.")
        @Min(value = 2021, message = "2021년도 이후 입학생만 가입 가능합니다.")
        Integer yearId,

        @NotNull(message = "학년은 필수입니다.")
        @Min(value = 1, message = "학년은 1학년 이상이어야 합니다.")
        @Max(value = 4, message = "학년은 4학년 이하여야 합니다.")
        Integer grade
) {}
