package com.gpyeong.core.domain.auth.application.dto.response;

import com.gpyeong.core.domain.common.domain.entity.GradeEnum;
import com.gpyeong.core.domain.auth.domain.entity.User;
import com.gpyeong.core.domain.auth.domain.entity.Gender;

public record ProfileResponse(
		Integer userId,
		String email,
		String name,
		Integer universityId,
		String department,
		Integer yearId,
		GradeEnum grade,
		Gender gender
) {
	public static ProfileResponse create(User user) {
		return new ProfileResponse(
				user.getUserId(),
				user.getEmail(),
				user.getName(),
				user.getUniversityId(),
				user.getDepartment(),
				user.getYearId(),
				user.getGrade(),
				user.getGender()
		);
	}
}


