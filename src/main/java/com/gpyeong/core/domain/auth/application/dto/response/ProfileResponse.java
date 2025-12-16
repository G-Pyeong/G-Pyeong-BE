package com.gpyeong.core.domain.auth.application.dto.response;

import com.gpyeong.core.domain.auth.domain.entity.User;

public record ProfileResponse(
		String userId,
		String email,
		String name,
		String school,
		String department,
		Integer admissionYear,
		Integer grade
) {
	public static ProfileResponse create(User user) {
		return new ProfileResponse(
				user.getUserId(),
				user.getEmail(),
				user.getName(),
				user.getSchool(),
				user.getDepartment(),
				user.getAdmissionYear(),
				user.getGrade()
		);
	}
}


