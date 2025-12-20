package com.gpyeong.core.domain.auth.application.dto.response;

import com.gpyeong.core.domain.auth.domain.entity.GradeId;
import com.gpyeong.core.domain.auth.domain.entity.User;

public record ProfileResponse(
		String userId,
		String email,
		String name,
		Integer universityId,
		String department,
		Integer yearId,
		GradeId gradeId
) {
	public static ProfileResponse create(User user) {
		return new ProfileResponse(
				user.getUserId(),
				user.getEmail(),
				user.getName(),
				user.getUniversityId(),
				user.getDepartment(),
				user.getYearId(),
				user.getGradeId()
		);
	}
}


