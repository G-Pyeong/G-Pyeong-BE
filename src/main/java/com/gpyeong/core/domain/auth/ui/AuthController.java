package com.gpyeong.core.domain.auth.ui;

import com.gpyeong.core.domain.auth.application.dto.request.LoginRequest;
import com.gpyeong.core.domain.auth.application.dto.request.SignUpRequest;
import com.gpyeong.core.domain.auth.application.dto.request.TokenReissueRequest;
import com.gpyeong.core.domain.auth.application.dto.response.LoginResponse;
import com.gpyeong.core.domain.auth.application.dto.response.TokenReissueResponse;
import com.gpyeong.core.domain.auth.application.usecase.UserAuthUseCase;
import com.gpyeong.core.global.annotation.CurrentUser;
import com.gpyeong.core.global.common.BaseResponse;
import com.gpyeong.core.global.swagger.AuthApi;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class AuthController implements AuthApi {

	private final UserAuthUseCase userAuthUseCase;

	@PostMapping("/signup")
	public BaseResponse<Void> signup(@Valid @RequestBody SignUpRequest request) {
		userAuthUseCase.signUp(request);
		return BaseResponse.onSuccess();
	}

	@PostMapping("/login")
	public BaseResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
		return BaseResponse.onSuccess(userAuthUseCase.login(request));
	}

	@DeleteMapping("/logout")
	@Override
	public BaseResponse<Void> logout(HttpServletRequest request) {
		userAuthUseCase.logout(request);
		return BaseResponse.onSuccess();
	}
	
	@PostMapping("/reissue")
	@Override
	public BaseResponse<TokenReissueResponse> reissueToken(@Valid @RequestBody TokenReissueRequest request) {
		return BaseResponse.onSuccess(userAuthUseCase.reissueToken(request));
	}
}