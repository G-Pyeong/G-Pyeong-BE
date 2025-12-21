package com.gpyeong.core.domain.auth.application.dto.request;

import com.gpyeong.core.domain.common.domain.entity.GradeEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateProfileRequest(
		@NotBlank String name,
		@NotBlank @Email String email,
		@NotBlank String department,
		@NotNull Integer yearId,
		@NotNull GradeEnum grade
) {}