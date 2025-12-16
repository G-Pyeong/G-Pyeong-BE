package com.gpyeong.core.domain.auth.application.usecase;

import static com.gpyeong.core.global.exception.code.status.GlobalErrorStatus._UNAUTHORIZED;

import com.gpyeong.core.domain.auth.application.dto.request.UpdateProfileRequest;
import com.gpyeong.core.domain.auth.application.dto.response.ProfileResponse;
import com.gpyeong.core.domain.auth.domain.entity.User;
import com.gpyeong.core.domain.auth.domain.service.UserService;
import com.gpyeong.core.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateProfileUseCase {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	public ProfileResponse update(String userId, UpdateProfileRequest request) {
		User user = userService.findUser(userId);

		String encodedNewPassword = null;

		// 새 비번 요청이 있는 경우 → 현재 비번 일치 여부 검사 (불일치 시 권한 오류)
		if (request.newPassword() != null && !request.newPassword().isBlank()) {
			boolean matches = passwordEncoder.matches(request.currentPassword(), user.getPassword());
			if (!matches) {
				throw new RestApiException(_UNAUTHORIZED);
			}
			encodedNewPassword = passwordEncoder.encode(request.newPassword());
		}

		user.updateProfile(request.name(), request.department(), request.admissionYear(), request.grade(), encodedNewPassword);
		return ProfileResponse.create(user);
	}
}


