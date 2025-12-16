package com.gpyeong.core.domain.auth.application.usecase;

import static com.gpyeong.core.global.exception.code.status.AuthErrorStatus.ALREADY_REGISTERED_EMAIL;
import static com.gpyeong.core.global.exception.code.status.AuthErrorStatus.ALREADY_REGISTERED_USER_ID;
import static com.gpyeong.core.global.exception.code.status.AuthErrorStatus.EMPTY_JWT;
import static com.gpyeong.core.global.exception.code.status.AuthErrorStatus.EXPIRED_REFRESH_TOKEN;
import static com.gpyeong.core.global.exception.code.status.AuthErrorStatus.INVALID_ACCESS_TOKEN;
import static com.gpyeong.core.global.exception.code.status.AuthErrorStatus.INVALID_REFRESH_TOKEN;
import static com.gpyeong.core.global.exception.code.status.AuthErrorStatus.LOGIN_ERROR;

import com.gpyeong.core.domain.auth.application.dto.request.LoginRequest;
import com.gpyeong.core.domain.auth.application.dto.request.SignUpRequest;
import com.gpyeong.core.domain.auth.application.dto.request.TokenReissueRequest;
import com.gpyeong.core.domain.auth.application.dto.response.LoginResponse;
import com.gpyeong.core.domain.auth.application.dto.response.TokenReissueResponse;
import com.gpyeong.core.domain.auth.domain.entity.User;
import com.gpyeong.core.domain.auth.domain.service.RefreshTokenService;
import com.gpyeong.core.domain.auth.domain.service.TokenBlacklistService;
import com.gpyeong.core.domain.auth.domain.service.TokenWhitelistService;
import com.gpyeong.core.domain.auth.domain.service.UserService;
import com.gpyeong.core.global.exception.RestApiException;
import com.gpyeong.core.global.security.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserAuthUseCase {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final TokenProvider tokenProvider;
	private final RefreshTokenService refreshTokenService;
	private final TokenWhitelistService tokenWhitelistService;
	private final TokenBlacklistService tokenBlacklistService;

	public void signUp(SignUpRequest request) {
		if (userService.isAlreadyRegistered(request.email())) {
			throw new RestApiException(ALREADY_REGISTERED_EMAIL);
		}
		if (userService.isUserIdAlreadyRegistered(request.userId())) {
			throw new RestApiException(ALREADY_REGISTERED_USER_ID);
		}
		userService.save(request);
	}

	public LoginResponse login(LoginRequest request) {
		User user = userService.findByUserId(request.userId());
		if (!passwordEncoder.matches(request.password(), user.getPassword())) {
			throw new RestApiException(LOGIN_ERROR);
		}
		String access = tokenProvider.createAccessToken(user.getUserId());
		String refresh = tokenProvider.createRefreshToken(user.getUserId());
		Duration ttl = tokenProvider.getRemainingDuration(refresh).orElse(Duration.ofDays(14));
		refreshTokenService.saveRefreshToken(user.getUserId(), refresh, ttl);
		return new LoginResponse(access, refresh);
	}

	public void logout(HttpServletRequest request) {
		String accessToken = tokenProvider.getToken(request)
				.orElseThrow(() -> new RestApiException(EMPTY_JWT));
		String userId = tokenProvider.getId(accessToken)
				.orElseThrow(() -> new RestApiException(INVALID_ACCESS_TOKEN));
		Duration expiration = tokenProvider.getRemainingDuration(accessToken)
				.orElseThrow(() -> new RestApiException(INVALID_ACCESS_TOKEN));
		refreshTokenService.deleteRefreshToken(userId);
		tokenWhitelistService.deleteWhitelistToken(accessToken);
		tokenBlacklistService.blacklist(accessToken, expiration);
	}
	
	public TokenReissueResponse reissueToken(TokenReissueRequest request) {
		String refreshToken = request.refreshToken();
		
		// 1. refresh token 유효성 검증
		if (!tokenProvider.validateToken(refreshToken)) {
			throw new RestApiException(INVALID_REFRESH_TOKEN);
		}
		
		// 2. refresh token에서 userId 추출
		String userId = tokenProvider.getId(refreshToken)
				.orElseThrow(() -> new RestApiException(INVALID_REFRESH_TOKEN));
		
		// 3. Redis에 저장된 refresh token과 일치하는지 확인
		String savedRefreshToken = refreshTokenService.findByUserId(userId);
		if (savedRefreshToken == null || !savedRefreshToken.equals(refreshToken)) {
			throw new RestApiException(INVALID_REFRESH_TOKEN);
		}
		
		// 4. refresh token 만료 시간 확인
		Duration remainingTime = tokenProvider.getRemainingDuration(refreshToken)
				.orElseThrow(() -> new RestApiException(EXPIRED_REFRESH_TOKEN));
		
		// 5. 새로운 access token 발급
		String newAccessToken = tokenProvider.createAccessToken(userId);
		
		// 6. 새로운 refresh token 발급 (기존 것 교체)
		String newRefreshToken = tokenProvider.createRefreshToken(userId);
		Duration newTtl = tokenProvider.getRemainingDuration(newRefreshToken).orElse(Duration.ofDays(14));
		
		// 7. 기존 refresh token 삭제하고 새로운 것 저장
		refreshTokenService.deleteRefreshToken(userId);
		refreshTokenService.saveRefreshToken(userId, newRefreshToken, newTtl);
		
		// 8. 기존 refresh token을 블랙리스트에 추가 (보안 강화)
		tokenBlacklistService.blacklist(refreshToken, remainingTime);
		
		return new TokenReissueResponse(
				newAccessToken, 
				newRefreshToken
		);
	}
}