package com.gpyeong.core.domain.auth.application.dto.response;

import com.gpyeong.core.domain.common.domain.entity.GradeEnum;
import com.gpyeong.core.domain.auth.domain.entity.User;

public record ProfileResponse(
		Integer userId,
		String email,
		String name,
		Integer universityId,
		String department,
		Integer yearId,
		GradeEnum grade
) {
	public static ProfileResponse create(User user) {
		return new ProfileResponse(
				user.getUserId(),
				user.getEmail(),
				user.getName(),
				user.getUniversityId(),
				user.getDepartment(),
				user.getYearId(),
				user.getGrade()
		);
	}
}


