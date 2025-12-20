package com.gpyeong.core.domain.auth.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GradeId {
	FIRST("1학년"),
	SECOND("2학년"),
	THIRD("3학년"),
	FOURTH("4학년");

	private final String description;
}
