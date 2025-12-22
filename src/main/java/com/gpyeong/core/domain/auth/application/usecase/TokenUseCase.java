package com.gpyeong.core.domain.auth.application.usecase;

import java.time.Duration;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gpyeong.core.domain.auth.application.dto.request.TokenReissueRequest;
import com.gpyeong.core.domain.auth.application.dto.response.TokenReissueResponse;
import com.gpyeong.core.domain.auth.domain.service.RefreshTokenService;
import com.gpyeong.core.domain.auth.domain.service.TokenWhitelistService;
import com.gpyeong.core.global.exception.RestApiException;
import com.gpyeong.core.global.exception.code.status.GlobalErrorStatus;
import com.gpyeong.core.global.security.TokenProvider;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TokenUseCase {
	
	private final TokenProvider tokenProvider;
	private final RefreshTokenService refreshTokenService;
	private final TokenWhitelistService tokenWhitelistService;
	
	public void logout(HttpServletRequest request) {
		tokenProvider.getToken(request).ifPresent(token -> {
			tokenWhitelistService.deleteWhitelistToken(token);
			log.info("User logged out, token removed from whitelist");
		});
	}
	
	public TokenReissueResponse reissueToken(TokenReissueRequest request) {
		String refreshToken = request.refreshToken();
		
		// Refresh Token 검증
		if (!tokenProvider.validateToken(refreshToken)) {
			throw new RestApiException(GlobalErrorStatus._UNAUTHORIZED);
		}
		
		// userId 추출
		Integer userId = tokenProvider.getId(refreshToken)
				.orElseThrow(() -> new RestApiException(GlobalErrorStatus._UNAUTHORIZED));
		
		// 저장된 Refresh Token과 비교
		String storedRefreshToken = refreshTokenService.findByUserId(userId);
		if (storedRefreshToken == null || !refreshToken.equals(storedRefreshToken)) {
			throw new RestApiException(GlobalErrorStatus._UNAUTHORIZED);
		}
		
		// 새로운 Access Token 발급
		String newAccessToken = tokenProvider.createAccessToken(userId);
		
		// 기존 Refresh Token 재사용 (만료 시간 갱신)
		Duration timeout = tokenProvider.getRemainingDuration(refreshToken)
				.orElse(Duration.ofDays(7));
		refreshTokenService.saveRefreshToken(userId, refreshToken, timeout);
		
		log.info("Token reissued for userId: {}", userId);
		
		return new TokenReissueResponse(newAccessToken, refreshToken);
	}
}
