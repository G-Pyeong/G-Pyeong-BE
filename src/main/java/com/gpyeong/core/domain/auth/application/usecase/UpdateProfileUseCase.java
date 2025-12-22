package com.gpyeong.core.domain.auth.application.usecase;

import com.gpyeong.core.domain.auth.application.dto.request.UpdateProfileRequest;
import com.gpyeong.core.domain.auth.application.dto.response.ProfileResponse;
import com.gpyeong.core.domain.auth.domain.entity.User;
import com.gpyeong.core.domain.auth.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateProfileUseCase {

	private final UserService userService;

	public ProfileResponse update(Integer userId, UpdateProfileRequest request) {
		User user = userService.findUser(userId);
		user.updateProfile(request.name(), request.department(), request.yearId(), request.grade());
		return ProfileResponse.create(user);
	}
}
