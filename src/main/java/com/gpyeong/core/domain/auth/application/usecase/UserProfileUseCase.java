package com.gpyeong.core.domain.auth.application.usecase;

import com.gpyeong.core.domain.auth.application.dto.response.ProfileResponse;
import com.gpyeong.core.domain.auth.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserProfileUseCase {

	private final UserService userService;

	public ProfileResponse findProfile(Integer userId) {
		return userService.findProfile(userId);
	}
}


