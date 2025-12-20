package com.gpyeong.core.global.security.oauth.handler;

import java.io.IOException;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.gpyeong.core.domain.auth.domain.service.RefreshTokenService;
import com.gpyeong.core.global.security.TokenProvider;
import com.gpyeong.core.global.security.oauth.service.CustomOAuth2User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	private final TokenProvider tokenProvider;
	private final RefreshTokenService refreshTokenService;

	@Value("${app.auth.redirect-url}")
	private String redirectUrl;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
		String userId = oAuth2User.getUserId();
		
		// JWT 토큰 생성
		String accessToken = tokenProvider.createAccessToken(userId);
		String refreshToken = tokenProvider.createRefreshToken(userId);
		
		// Refresh Token 저장 (7일)
		refreshTokenService.saveRefreshToken(userId, refreshToken, Duration.ofDays(7));
		
		log.info("OAuth2 Login Success - userId: {}, isNewUser: {}", userId, oAuth2User.isNewUser());

		// 프론트엔드 콜백 URL로 리다이렉트
		String targetUrl = UriComponentsBuilder.fromUriString(redirectUrl)
			.queryParam("accessToken", accessToken)
			.queryParam("refreshToken", refreshToken)
			.queryParam("isNewUser", oAuth2User.isNewUser())
			.build().toUriString();

		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}
}
