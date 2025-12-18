package com.gpyeong.core.domain.auth.domain.service;

import static com.gpyeong.core.global.exception.code.status.GlobalErrorStatus._NOT_FOUND;

import com.gpyeong.core.domain.auth.application.dto.request.SignUpRequest;
import com.gpyeong.core.domain.auth.application.dto.response.ProfileResponse;
import com.gpyeong.core.domain.auth.domain.entity.User;
import com.gpyeong.core.domain.auth.domain.repository.UserRepository;
import com.gpyeong.core.global.exception.RestApiException;
import com.gpyeong.core.global.exception.code.status.GlobalErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final EmailVerificationService emailVerificationService;

	public User findByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new RestApiException(_NOT_FOUND));
	}
	
	public User findByUserId(String userId) {
		return userRepository.findByUserId(userId)
				.orElseThrow(() -> new RestApiException(_NOT_FOUND));
	}

	public boolean isAlreadyRegistered(String email) {
		return userRepository.existsByEmail(email);
	}
	
	public boolean isUserIdAlreadyRegistered(String userId) {
		return userRepository.existsByUserId(userId);
	}

	public User save(SignUpRequest request) {
		if (!emailVerificationService.isVerified(request.email())) {
			throw new RestApiException(GlobalErrorStatus._UNAUTHORIZED);
		}

		User user = User.builder()
				.userId(request.userId())
				.email(request.email())
				.password(passwordEncoder.encode(request.password()))
				.name(request.name())
				.universityId(request.universityId())
				.department(request.department())
				.yearId(request.yearId())
				.grade(request.grade())
				.build();
		return userRepository.save(user);
	}

	public User findUser(String userId) {
		return userRepository.findByUserId(userId)
				.orElseThrow(() -> new RestApiException(_NOT_FOUND));
	}

	public ProfileResponse findProfile(String userId) {
		User user = userRepository.findByUserId(userId)
				.orElseThrow(() -> new RestApiException(_NOT_FOUND));
		return ProfileResponse.create(user);
	}
}