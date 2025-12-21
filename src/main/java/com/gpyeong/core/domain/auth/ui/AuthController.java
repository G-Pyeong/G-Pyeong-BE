package com.gpyeong.core.domain.auth.ui;

import com.gpyeong.core.domain.auth.application.dto.request.SignUpRequest;
import com.gpyeong.core.domain.auth.application.dto.request.TokenReissueRequest;
import com.gpyeong.core.domain.auth.application.dto.response.LoginResponse;
import com.gpyeong.core.domain.auth.application.dto.response.TokenReissueResponse;
import com.gpyeong.core.domain.auth.application.usecase.TokenUseCase;
import com.gpyeong.core.domain.auth.domain.service.UserService;
import com.gpyeong.core.global.annotation.CurrentUser;
import com.gpyeong.core.global.common.BaseResponse;
import com.gpyeong.core.global.swagger.AuthApi;
import com.gpyeong.core.global.swagger.OnboardingApi;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class AuthController implements AuthApi, OnboardingApi {

	private final TokenUseCase tokenUseCase;
	private final UserService userService;

	/**
	 * 구글 로그인 시작 (Swagger 문서화용 리다이렉트 엔드포인트)
	 */
	@GetMapping("/login/google")
	@Override
	public void googleLogin(HttpServletResponse response) throws IOException {
		response.sendRedirect("/oauth2/authorization/google");
	}

	/**
	 * 로그인 성공 확인 (서버 전용 테스트용)
	 */
	@GetMapping("/login/success")
	@Override
	public BaseResponse<LoginResponse> loginSuccess(
			String accessToken,
			String refreshToken,
			Boolean isNewUser) {
		return BaseResponse.onSuccess(new LoginResponse(accessToken, refreshToken, isNewUser));
	}

	/**
	 * 온보딩 완료 - OAuth 로그인 후 추가 정보 입력
	 */
	@PostMapping("/onboarding")
	@Override
	public BaseResponse<Void> completeOnboarding(
			@CurrentUser Integer userId,
			@Valid @RequestBody SignUpRequest request) {
		userService.completeOnboarding(userId, request);
		return BaseResponse.onSuccess();
	}

	@DeleteMapping("/logout")
	@Override
	public BaseResponse<Void> logout(HttpServletRequest request) {
		tokenUseCase.logout(request);
		return BaseResponse.onSuccess();
	}
	
	@PostMapping("/reissue")
	@Override
	public BaseResponse<TokenReissueResponse> reissueToken(@Valid @RequestBody TokenReissueRequest request) {
		return BaseResponse.onSuccess(tokenUseCase.reissueToken(request));
	}
}