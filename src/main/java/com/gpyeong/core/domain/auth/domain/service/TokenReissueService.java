package com.gpyeong.core.domain.auth.domain.service;

import static com.gpyeong.core.global.exception.code.status.AuthErrorStatus.EXPIRED_MEMBER_JWT;
import static com.gpyeong.core.global.exception.code.status.AuthErrorStatus.INVALID_REFRESH_TOKEN;

import com.gpyeong.core.domain.auth.application.dto.response.TokenReissueResponse;
import com.gpyeong.core.domain.auth.domain.entity.User;
import com.gpyeong.core.global.exception.RestApiException;
import com.gpyeong.core.global.security.TokenProvider;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenReissueService {

	private final TokenProvider tokenProvider;
	private final RefreshTokenService refreshTokenService;
	private final UserService userService;

	public TokenReissueResponse reissue(String refreshToken, Integer userId) {

		// 존재 유무 검사
		if (!refreshTokenService.isExist(refreshToken, userId)) {
			throw new RestApiException(INVALID_REFRESH_TOKEN);
		}

		// 기존에 있는 토큰 삭제
		refreshTokenService.deleteRefreshToken(userId);

		// 새 토큰 발급
		User user = userService.findUser(userId);
		String newAccessToken = tokenProvider.createAccessToken(userId);
		String newRefreshToken = tokenProvider.createRefreshToken(userId);
		Duration duration = tokenProvider.getRemainingDuration(refreshToken)
				.orElseThrow(() -> new RestApiException(EXPIRED_MEMBER_JWT));

		// 저장
		refreshTokenService.saveRefreshToken(userId, newRefreshToken, duration);

		return new TokenReissueResponse(newAccessToken, newRefreshToken);
	}
}