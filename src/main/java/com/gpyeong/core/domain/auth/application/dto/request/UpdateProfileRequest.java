package com.gpyeong.core.domain.auth.application.dto.request;

import com.gpyeong.core.domain.auth.domain.entity.GradeId;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateProfileRequest(
		@NotBlank String name,
		@NotBlank @Email String email,
		@NotBlank String department,
		@NotNull Integer yearId,
		@NotNull GradeId gradeId
) {}