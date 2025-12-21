package com.gpyeong.core.domain.auth.domain.service;

import static com.gpyeong.core.global.exception.code.status.GlobalErrorStatus._NOT_FOUND;

import com.gpyeong.core.domain.auth.application.dto.request.SignUpRequest;
import com.gpyeong.core.domain.auth.application.dto.response.ProfileResponse;
import com.gpyeong.core.domain.auth.domain.entity.User;
import com.gpyeong.core.domain.auth.domain.repository.UserRepository;
import com.gpyeong.core.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public User findByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new RestApiException(_NOT_FOUND));
	}
	
	public User findByUserId(Integer userId) {
		return userRepository.findByUserId(userId)
				.orElseThrow(() -> new RestApiException(_NOT_FOUND));
	}

	public boolean isAlreadyRegistered(String email) {
		return userRepository.existsByEmail(email);
	}
	
	public boolean isUserIdAlreadyRegistered(Integer userId) {
		return userRepository.existsByUserId(userId);
	}

	/**
	 * 온보딩 완료 - OAuth 로그인 이후 사용자 추가 정보 업데이트
	 */
	public User completeOnboarding(Integer userId, SignUpRequest request) {
		User user = findByUserId(userId);
		
		// 온보딩 정보 업데이트
		user = User.builder()
				.userId(user.getUserId())
				.email(user.getEmail())
				.name(request.name())
				.providerId(user.getProviderId())
				.provider(user.getProvider())
				.universityId(request.universityId())
				.department(request.department())
				.yearId(request.yearId())
				.grade(request.grade())
				.build();
		
		return userRepository.save(user);
	}

	public User findUser(Integer userId) {
		return userRepository.findByUserId(userId)
				.orElseThrow(() -> new RestApiException(_NOT_FOUND));
	}

	public ProfileResponse findProfile(Integer userId) {
		User user = userRepository.findByUserId(userId)
				.orElseThrow(() -> new RestApiException(_NOT_FOUND));
		return ProfileResponse.create(user);
	}
}