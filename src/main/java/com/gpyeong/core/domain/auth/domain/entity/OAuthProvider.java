package com.gpyeong.core.domain.auth.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OAuthProvider {
	GOOGLE("google");

	private final String registrationId;
}
