package com.gpyeong.core.domain.auth.ui;

import com.gpyeong.core.domain.auth.application.dto.request.UpdateProfileRequest;
import com.gpyeong.core.domain.auth.application.dto.response.ProfileResponse;
import com.gpyeong.core.domain.auth.application.usecase.UpdateProfileUseCase;
import com.gpyeong.core.domain.auth.application.usecase.UserProfileUseCase;
import com.gpyeong.core.global.annotation.CurrentUser;
import com.gpyeong.core.global.common.BaseResponse;
import com.gpyeong.core.global.swagger.UserProfileApi;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController implements UserProfileApi {

	private final UserProfileUseCase userProfileUseCase; // 조회
	private final UpdateProfileUseCase updateProfileUseCase; // 수정

	@GetMapping("/profile")
	@Override
	public BaseResponse<ProfileResponse> getProfile(
			@Parameter(hidden = true) @CurrentUser Integer userId) {
		ProfileResponse profile = userProfileUseCase.findProfile(userId);
		return BaseResponse.onSuccess(profile);
	}

	@PatchMapping("/profile")
	@Override
	public BaseResponse<ProfileResponse> updateProfile(
			@Parameter(hidden = true) @CurrentUser Integer userId,
			@Valid @RequestBody UpdateProfileRequest request) {
		ProfileResponse updated = updateProfileUseCase.update(userId, request);
		return BaseResponse.onSuccess(updated);
	}
}