package com.gpyeong.core.domain.auth.application.dto.response;

public record TokenReissueResponse(
		String accessToken,
		String refreshToken
) {}


