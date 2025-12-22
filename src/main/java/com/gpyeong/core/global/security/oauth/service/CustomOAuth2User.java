package com.gpyeong.core.global.security.oauth.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.gpyeong.core.domain.auth.domain.entity.User;
import com.gpyeong.core.global.security.oauth.principal.SocialPrincipal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {
	
	private final SocialPrincipal principal;
	private final User user;
	
	@Override
	public Map<String, Object> getAttributes() {
		return Collections.emptyMap();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
	}
	
	@Override
	public String getName() {
		return user.getName();
	}
	
	public Integer getUserId() {
		return user.getUserId();
	}
	
	public boolean isNewUser() {
		// 온보딩이 필요한지 확인 (universityId가 없으면 신규 사용자)
		return user.getUniversityId() == null;
	}
}
