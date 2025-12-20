package com.gpyeong.core.domain.auth.application.dto.response;

public record LoginResponse(
		String accessToken,
		String refreshToken,
		Boolean isNewUser
) {}
